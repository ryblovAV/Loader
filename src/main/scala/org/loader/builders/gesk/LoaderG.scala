package org.loader.builders.gesk


import grizzled.slf4j.Logging
import org.loader.builders.general.{KeysBuilder, DateBuilder}
import org.loader.db.dao.general.GeneralDAO
import org.loader.models.{SubjectModel, ObjectModel}
import org.loader.out.gesk.objects.{Potr, Plat}
import org.loader.out.gesk.reader.GeskReader
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sa.SaEntity
import org.loader.writer.LogWritter
import org.springframework.context.support.ClassPathXmlApplicationContext
object LoaderG extends Logging{

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val generalDAO = ctx.getBean(classOf[GeneralDAO])

  val activeMonth = DateBuilder.getDate(2015,2,1)
  val stopHistoricalDt = DateBuilder.addMinute(activeMonth,-1)
  val readDttm = DateBuilder.lastDay(activeMonth)

  def findPer(perId: String) = {
    generalDAO.findPer(perId)
  }

  def removePer(perId: String) = {
    generalDAO.removePer(perId)
  }

  def potrToObject(plat: Plat, potr: Potr, mPremise: Map[String, PremEntity]): ObjectModel = {

    val sp = SpBuilderG.build(potr = potr, premise = mPremise(potr.idObj))
    val mtr = MtrBuilderG.build(potr)
    val mtrCfg = MtrConfigBuilderG.build(mtr = mtr, potr = potr)

    val mr = MrBuilderG.build(readDttm = readDttm,
      mtrConfig = mtrCfg)

    if (potr.isMultiZone) {
      for ((p,i) <- potr.zone.listZonePotr.view.zipWithIndex) {
        RegReadBuilderG.build(
          mr = mr,
          reg = RegBuilderG.build(mtr = mtr, potr = p, isMultiZone = true, seq = i),
          regReading = p.mt.r2)
      }
    } else {
      RegReadBuilderG.build(
        mr = mr,
        reg = RegBuilderG.build(mtr = mtr, potr = potr, isMultiZone = false, seq = 1),
        regReading = potr.mt.r2)
    }

    val spMtrHist = SpMtrHistBuilderG.build(sp = sp, mtrCfg = mtrCfg)

    MtrLocHisBuilderG.build(mtr = mtr, spMtrHist = spMtrHist, readDttm = readDttm)

    SpMtrEvtBuilderG.build(spMtrHist = spMtrHist, mr = mr)

    //TODO Если NO_RSCH  = true – то по точке не производим расчет (не привязываем к РДО
    val sa = SaBuilderG.buildSaForSp(plat, potr, mPremise(potr.idObj))

    SaSpBuilderG.buildSaSp(sa = sa, sp = sp, mr = mr)

    //load finance
    loadFinance(potr,sa)

    ObjectModel(potr, sp,sa, mr)
  }

  def loadFinance(potr:Potr, sa:SaEntity) = {
    for (saldo <- potr.saldo) {
      val adj = AdjBuilderG.build(sa = sa, adjAmt = saldo)
      FtBuilderG.build(adjId = adj.adjId, sa = sa, curAmt = saldo)
    }
  }

  def platToSubject(plat: Plat):SubjectModel = {

    val per = PersonBuilderG.buildPerson(plat)
    val acct = AccountBuilderG.buildAccount(plat)

    //создаем premise для каждого id
    val mPremise: Map[String, PremEntity] =
      plat.potrList.
        groupBy((p) => p.idObj).
        mapValues((pl) => pl.head).
        mapValues((p) => PremiseBuilderG.buildPremise(p))

    val objects:List[ObjectModel] = plat.potrList.map((potr) => potrToObject(plat,potr,mPremise))

    //add service agreement to account
    objects.foreach((o) => acct.addSaEntity(o.sa))

    PersonBuilderG.addAcctToPer(per, acct)

    SubjectModel(plat = plat, per = per, acct = acct, objects = objects)
  }


  def addToParent(parIdRec: String, obj: ObjectModel, m:Map[String,ObjectModel]) = {
    for (parentSubj <- m.get(obj.potr.idRecI)) {
      SaSpBuilderG.buildSaSp(sa = parentSubj.sa, sp = obj.sp, mr = obj.mr)
    }
  }

  def linkToParent(obj: ObjectModel, m:Map[String,ObjectModel]) = {
    if (obj.potr.idRecI != "0") {
      addToParent(parIdRec = obj.potr.idRecI,obj = obj, m = m)
    }

    for (parentIdRec <- obj.potr.parentIdRec) {
      addToParent(parIdRec = parentIdRec,obj = obj, m = m)
    }
  }

  def transforEntity(subjects: List[SubjectModel]) = {
    val m = Map.empty[String,ObjectModel] ++
      (subjects.map((s)=>s.objects).flatten.map((o) => (o.potr.idRec,o)))
    m.values.foreach((o) => linkToParent(o,m))
  }

  def checkZonePort(potr: Potr, mPotrForIdGrup:Map[Int,List[Potr]]):Option[Potr] =
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

  def filterZonePotrForPlat(plat: Plat, mPotrForIdGrup:Map[Int,List[Potr]]):List[Potr] = {
    for {
      potr <- plat.potrList
      potrWithChilds <- checkZonePort(potr, mPotrForIdGrup)
    } yield potrWithChilds
  }

  def fillZonePotr(platList: List[Plat]):List[Plat] = {

    // list potr for group
    val mPotrForIdGrup:Map[Int,List[Potr]] = (for {
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
    val platList = fillZonePotr(GeskReader.readPlat.filter(_.idPlat == "7071"))

    info("------------ start build subjectList")
    val subjects = platList.map((plat) => platToSubject(plat))

    info("------------ start transformation")
    transforEntity(subjects)

    info("------------ start load tndr")
//    val depCtlSt = loadTndr(subjects = subjects)

    info("------------ start saveToDb")
    generalDAO.saveList(subjects)
//    generalDAO.saveDepCtlSt(depCtlSt = depCtlSt)

    info("------------ start logging")
    for (subj <- subjects) {
      LogWritter.log(subj = subj)
    }
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
