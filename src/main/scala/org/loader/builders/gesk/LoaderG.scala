package org.loader.builders.gesk


import grizzled.slf4j.Logging
import org.loader.builders.general.{KeysBuilder, DateBuilder}
import org.loader.db.dao.general.GeneralDAO
import org.loader.models.{SpObject, SubjectModel, ObjectModel}
import org.loader.out.gesk.objects.{Potr, Plat}
import org.loader.out.gesk.reader.GeskReader
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.reg.RegEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sp.SpEntity
import org.loader.writer.{SaSpWriter, SaSpObject, LogWritter}
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

  def buildReg(potr:Potr, mtr:MtrEntity, seq:Int):(RegEntity,Potr) = {
    (RegBuilderG.build(mtr = mtr, potr = potr, isMultiZone = true, seq = seq),potr)
  }

  def buildRegRead(potr: Potr, reg: RegEntity, mrFirst: MrEntity, mrLast: MrEntity):Unit = {
    if (potr.isHistVol) {

      RegReadBuilderG.build(
        mr = mrFirst,
        reg = reg,
        regReading = 0)

      RegReadBuilderG.build(
        mr = mrLast,
        reg = reg,
        regReading = potr.mt.rUz.get)

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

  def potrToSpObject(potr: Potr, mPremise: Map[String, PremEntity], isOrphan: Boolean):SpObject = {

    val premise = mPremise(potr.idObj)

    val sp = SpBuilderG.build(potr = potr, premise = premise, isOrphan = isOrphan)
    val mtr = MtrBuilderG.build(potr)
    val mtrCfg = MtrConfigBuilderG.build(mtr = mtr, potr = potr)

    val mrFirst = MrBuilderG.build(readDttm = activeMonth, mtrConfig = mtrCfg)
    val mrLast =  MrBuilderG.build(readDttm = readDttm, mtrConfig = mtrCfg)

    val regList = potr.zone.listZonePotr.view.zipWithIndex.map(
      (p:(Potr,Int)) => buildReg(potr = p._1,mtr = mtr, seq = p._2)
    ).toList

    if (potr.isMultiZone) {

      for ((reg,potr) <- regList) {

        buildRegRead(potr = potr,
          reg = reg,
          mrFirst = mrFirst,
          mrLast = mrLast)

      }
    } else {

      buildRegRead(potr = potr,
        reg = RegBuilderG.build(mtr = mtr, potr = potr, isMultiZone = false, seq = 1),
        mrFirst = mrFirst,
        mrLast = mrLast)

    }

    val spMtrHist = SpMtrHistBuilderG.build(sp = sp, mtrCfg = mtrCfg)

    MtrLocHisBuilderG.build(mtr = mtr, spMtrHist = spMtrHist, readDttm = readDttm)

    SpMtrEvtBuilderG.build(spMtrHist = spMtrHist, mr = mrFirst)

    SpObject(potr = potr, sp = sp, mrFirst = mrFirst, mrLast = mrLast, regList = regList)
  }

  def potrToObject(plat: Plat, potr: Potr, mPremise: Map[String, PremEntity]): ObjectModel = {

    val spObj = potrToSpObject(potr = potr, mPremise = mPremise, isOrphan = false)

    //TODO Если NO_RSCH  = true – то по точке не производим расчет (не привязываем к РДО
    val sa = SaBuilderG.buildSaForSp(plat, potr, mPremise(potr.idObj))

    SaSpBuilderG.buildSaSp(sa = sa, sp = spObj.sp, mr = spObj.mrFirst, isMinus = false)

    //load finance
    //loadFinance(potr,sa)

    ObjectModel(
      potr = potr,
      sp = spObj.sp,
      sa = sa,
      mrFirst = spObj.mrFirst,
      mrLast = spObj.mrLast,
      regList  = spObj.regList)
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

    val objects:List[ObjectModel] = plat.potrList
      .filter((potr) => !potr.isOrphan)
      .map((potr) => potrToObject(plat,potr,mPremise))


    //build sp for physics
    //shared objects without parent
    val spObjects = plat.potrList.filter(_.isOrphan).map((potr) => potrToSpObject(potr = potr, mPremise = mPremise, isOrphan = true))

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

    info("------------ start saveToDb")
    subjects.grouped(1000).toList.par.foreach(generalDAO.saveList)
//    subjects.foreach(generalDAO.save)

    info("start save to DB shared objects")
    SaSpWriter.save(saSpObjects)

//    generalDAO.saveDepCtlSt(depCtlSt = depCtlSt)

    info("------------ start logging")
    LogWritter.log(subjects = subjects)
    info("------------ end")
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
