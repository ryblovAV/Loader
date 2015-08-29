package org.loader.builders.gesk


import grizzled.slf4j.Logging
import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.db.dao.general.GeneralDAO
import org.loader.models.{ObjectModel, SpObject, SubjectModel}
import org.loader.out.gesk.objects.{Per, Plat, Potr}
import org.loader.out.gesk.reader.GeskReader
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.reg.RegEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sp.SpEntity
import org.loader.writer.{SaSpObject, LogWritter, SaSpWriter}
import org.springframework.context.support.ClassPathXmlApplicationContext
object LoaderG extends Logging{

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val generalDAO = ctx.getBean(classOf[GeneralDAO])

  val activeMonth = DateBuilder.getDate(2015,6,1)
  val stopHistoricalDt = DateBuilder.addMinute(activeMonth,-1)
  val readDttm = DateBuilder.lastDay(activeMonth)

  def findPer(perId: String) = {
    generalDAO.findPer(perId)
  }

  def removePer(perId: String) = {
    generalDAO.removePer(perId)
  }

  def buildReg(potr:Potr, mtr:MtrEntity, isHistVol: Boolean, seq:Int):(RegEntity,Potr) = {
    (RegBuilderG.build(mtr = mtr, potr = potr, isMultiZone = true, isHistVol = isHistVol, seq = seq),potr)
  }

  def buildRegRead(potr: Potr, reg: RegEntity, mrFirst: MrEntity, mrLast: MrEntity, isHistVol: Boolean):Unit = {
    if (isHistVol) {

      RegReadBuilderG.build(
        mr = mrFirst,
        reg = reg,
        regReading = 0)

      RegReadBuilderG.build(
        mr = mrLast,
        reg = reg,
        regReading = potr.mt.d1.get * potr.mt.d3.get * potr.mt.d5.get)

    } else {

      RegReadBuilderG.build(
        mr = mrFirst,
        reg = reg,
        regReading = potr.mt.r1.getOrElse(0))

      RegReadBuilderG.build(
        mr = mrLast,
        reg = reg,
        regReading = potr.mt.r2.getOrElse(0))

    }
  }

  def potrToSpObject(potr: Potr, mPremise: Map[String, PremEntity], isOrphan: Boolean, isHistVol: Boolean):SpObject = {

    val premise = mPremise(potr.idObj)

    val sp = SpBuilderG.build(potr = potr, premise = premise, isOrphan = isOrphan)

    if (!potr.k.isFill) {

      val mtr = MtrBuilderG.build(potr,isHistVol)
      val mtrCfg = MtrConfigBuilderG.build(mtr = mtr, potr = potr)

      val mrFirst = MrBuilderG.build(readDttm = activeMonth, mtrConfig = mtrCfg)
      val mrLast =  MrBuilderG.build(readDttm = readDttm, mtrConfig = mtrCfg)

      val regList = potr.zone.listZonePotr.view.zipWithIndex.map(
        (p:(Potr,Int)) => buildReg(potr = p._1,mtr = mtr, seq = p._2,isHistVol = isHistVol)
      ).toList

      if (potr.isMultiZone) {

        for ((reg,potr) <- regList) {

          buildRegRead(
            potr = potr,
            reg = reg,
            mrFirst = mrFirst,
            mrLast = mrLast,
            isHistVol = isHistVol)

        }
      } else {

        buildRegRead(potr = potr,
          reg = RegBuilderG.build(mtr = mtr, potr = potr, isMultiZone = false, isHistVol = isHistVol, seq = 1),
          mrFirst = mrFirst,
          mrLast = mrLast,
          isHistVol = isHistVol
        )

      }

      val spMtrHist = SpMtrHistBuilderG.build(sp = sp, mtrCfg = mtrCfg)

      MtrLocHisBuilderG.build(mtr = mtr, spMtrHist = spMtrHist, readDttm = readDttm)

      SpMtrEvtBuilderG.build(spMtrHist = spMtrHist, mr = mrFirst)

      SpObject(
        potr = potr,
        sp = sp,
        mrFirst = Some(mrFirst),
        mrLast = Some(mrLast),
        regList = regList,
        spTypeCd = sp.spTypeCd + (if (isHistVol) "~H" else "")
      )

    } else {

      SpObject(
        potr = potr,
        sp = sp,
        mrFirst = None,
        mrLast = None,
        regList = List.empty,
        spTypeCd = sp.spTypeCd + (if (isHistVol) "~H" else ""))

    }



  }

  def potrToObject(plat: Plat, potr: Potr, mPremise: Map[String, PremEntity], saUnion:Option[SaEntity]): ObjectModel = {



    val spObj = potrToSpObject(potr = potr, mPremise = mPremise, isOrphan = false, isHistVol = false)

    val spObjects = if ((potr.mt.isHistVol) && (!potr.k.isFill)) {
      List(spObj,
           potrToSpObject(potr,mPremise, isOrphan = false, isHistVol = potr.mt.isHistVol))
    } else List(spObj)


    //TODO Если NO_RSCH  = true – то по точке не производим расчет (не привязываем к РДО


    val (sa,isMinus) = saUnion match {
      case Some(sa) => (sa,potr.naimp.take(1) == "-")
      case None => (SaBuilderG.buildSaForSp(plat, potr, mPremise(potr.idObj)),false)
    }

    for (spObj <- spObjects)
      SaSpBuilderG.buildSaSp(sa = sa, sp = spObj.sp, mr = spObj.mrFirst, isMinus = isMinus)


    //load finance
    //loadFinance(potr,sa)

    ObjectModel(
      potr = potr,
      spObjects = spObjects,
      sa = sa)

  }

  def loadFinance(plat:Plat) = {

//    for (saldo <- plat.finance.saldo) {
//
//
//
//      val adj = AdjBuilderG.build(sa = sa, adjAmt = saldo)
//      FtBuilderG.build(adjId = adj.adjId, sa = sa, curAmt = saldo)
//    }
  }

  def createSaUnion(plat:Plat, mPremise: Map[String, PremEntity]):Option[SaEntity] = {
    plat.potrList.filter((potr) => SaBuilderG.checkCkNet(potr.tar.cK.getOrElse(""))) match {
      case potr::_ => Some(SaBuilderG.buildSaForSp(plat, potr, mPremise(potr.idObj)))
      case _ => None
    }
  }

  def loadSaldo(plat: Plat, objects:Seq[ObjectModel]) = {
    for (saldo <- plat.finance.saldo) {
      for (obj <- objects.sortBy((o) => o.sa.saId).headOption) {
        val adj = AdjBuilderG.build(sa = obj.sa, adjAmt = saldo)
        FtBuilderG.build(adjId = adj.adjId, sa = obj.sa, curAmt = saldo)
      }
    }
  }

  def checkRecalculationPremiseUnion(plat:Plat) = {
    plat.perList.headOption match {
      case Some(per) if per.kp == 0 => true
      case _ => false
    }
  }

  def buildSaReCalc(plat:Plat,
                    potr:Potr,
                    perList:List[Per],
                    charPrem:PremEntity) = {

    val sa = SaBuilderG.buildSaForSp(
      plat = plat,
      potr = potr,
      charPrem = charPrem)

    sa.saTypeCd = "G_RECCUL"

    for (per <- perList) {
      sa.billChgEntitySet.add(BillChangeBuilderG.build(per,sa))
    }

    sa
  }

  def loadRecalculation(plat: Plat, mPremise: Map[String, PremEntity]) = {

    if (checkRecalculationPremiseUnion(plat)) {

      for {
        potr <- plat.potrList.sortBy(_.id).headOption.toList
      } yield buildSaReCalc(plat, potr, plat.perList, mPremise(potr.idObj))

    } else {

      for {
        (idObj, perList) <- plat.perList.groupBy(_.idObj)
        charPrem <- mPremise.get(idObj)
        potr <- plat.potrList.filter(_.idObj == idObj).sortBy(_.id).headOption
      } yield buildSaReCalc(plat, potr, perList, charPrem)

    }

  }

  def platToSubject(plat: Plat):SubjectModel = {

    val per = PersonBuilderG.buildPerson(plat)

    val acct = AccountBuilderG.buildAcct(plat = plat)

    //acct apay (БИК)
    AcctApayBuilderG.buildAcctApay(plat = plat, acct = acct)

    val mPremise: Map[String, PremEntity] =
      plat.potrList.groupBy((p) => p.idObj).
        mapValues((l) => l.head).
        values.map((potr) => (potr.idObj -> PremiseBuilderG.buildPremise(
          address = potr.address.copy(reg = Some("г.Липецк"), ind = Some("398000"), kv = Option(potr.naimp)),
          optKniga = potr.kniga))).
        toMap

    val saUnion = createSaUnion(plat,mPremise)

    val objects:List[ObjectModel] = plat.potrList
      .filter((potr) => !potr.isOrphan)
      .map((potr) => potrToObject(plat,potr,mPremise,saUnion))

    //saldo
    loadSaldo(plat = plat,objects = objects)

    //load recalculation
    loadRecalculation(plat,mPremise).foreach((sa) => acct.addSaEntity(sa))

    //build sp for physics
    //shared objects without parent
    val spObjects = plat.potrList.filter(_.isOrphan).map((potr) => potrToSpObject(potr = potr, mPremise = mPremise, isOrphan = true, isHistVol = potr.mt.isHistVol))

    //add service agreement to account
    objects.foreach((o) => acct.addSaEntity(o.sa))

    val acctPer = PersonBuilderG.addAcctToPer(per, acct)

    //add mailing address
    AccountBuilderG.addMailingAddress(plat = plat,per = per, acct = acct, acctPer = acctPer)

    SubjectModel(plat = plat, per = per, acct = acct, objects = objects, spObjects = spObjects)
  }

  def checkZonePort(potr: Potr, mPotrForIdGrup:Map[String,List[Potr]]):Option[Potr] =
    potr.zone.idGrup match {
      case Some(idGrup) => mPotrForIdGrup.get(idGrup) match {
        case Some(l) => l match {
          case h::_ if  (h.id == potr.id) => Some(potr.copy(zone = potr.zone.copy(listZonePotr = l)))
          case _ => None
        }
        case None => None
      }
      case None => Some(potr)
  }

  def filterZonePotrForPlat(plat: Plat, mPotrForIdGrup:Map[String,List[Potr]]):List[Potr] = {
    for {
      potr <- plat.potrList
      potrWithChilds <- checkZonePort(potr, mPotrForIdGrup)
    } yield potrWithChilds
  }

  def fillZonePotr(platList: List[Plat]):List[Plat] = {

    // list potr for group
    val mPotrForIdGrup:Map[String,List[Potr]] = (for {
      plat <- platList
      potr <- plat.potrList
      idGrup <- potr.zone.idGrup
    } yield (potr)).groupBy((potr) => potr.zone.idGrup.get).
      mapValues((listPotr) => listPotr.sortBy(_.id))

    //filter port many zones
    platList.map(
      (plat) => plat.copy(potrList = filterZonePotrForPlat(plat = plat, mPotrForIdGrup = mPotrForIdGrup)))
  }

  def removeAll = {
    val perIdList = GeskReader.readLoadedPerson
    info(s"perId = $perIdList")
    generalDAO.removePerList(perIdList.take(1))
//    generalDAO.removeSpList(GeskReader.readLoadedSp)
  }

  def loadPlat = {
    info("------------ start read from db")
    val platList = fillZonePotr(GeskReader.readPlat)

    info("------------ start build subjectList (par)")
    val subjects = platList.map((plat) => {
      platToSubject(plat)
    })

    info("------------ start set ref sa sp")
    val saSpObjects = SharedBuilderG.buildListSaSpObject(subjects)

    //    info("------------ start load tndr")
    //    val depCtlSt = loadTndr(subjects = subjects)


    if (true) {

      info("------------ start logging")
      LogWritter.log(subjects = subjects)
      LogWritter.logKeys(KeysBuilder.createdKeys)

      info("------------ end")

      info("------------ start saveToDb")
      subjects.grouped(1000).toList.par.foreach(generalDAO.saveList)
//      subjects.foreach((s) => generalDAO.save(s))

      info("start save to DB shared objects")
      SaSpWriter.save(saSpObjects)

      //    generalDAO.saveDepCtlSt(depCtlSt = depCtlSt)

    }

  }



//  def loadTndr(subjects: List[SubjectModel]) = {
//
//    val subjOplata = for {
//      subj <- subjects
//      dat <- subj.plat.oplataDat
//      sum <- subj.plat.oplataSum
//    } yield subj
//
//    val sumPayAmt = subjOplata.foldLeft(0d)((sum,subj) => sum + subj.plat.oplataSum.get)
//    val cntPay = subjOplata.size
//
//    val depCtlSt = TndrBuilderG.buildDept(dt = LoaderG.activeMonth, sumPayAmt = sumPayAmt)
//    val tndrCtlSt = TndrBuilderG.buildTndr(depCtlStPk = depCtlSt.deptCtlStPk, sumPayAmt = sumPayAmt, cntPay = cntPay)
//
//    subjOplata.foreach((s) => TndrBuilderG.buildPayTndr(
//      depCtlStPk = depCtlSt.deptCtlStPk,
//      extBatchId = tndrCtlSt.id.extBatchId,
//      tenderAmt = s.plat.oplataSum.get,
//      accountDt = s.plat.oplataDat.get,
//      acctId = s.acct.acctId
//    ))
//
//    depCtlSt
//
//  }




  
}
