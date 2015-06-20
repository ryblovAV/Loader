package org.loader.builders.gesk


import org.loader.db.dao.general.GeneralDAO
import org.loader.pojo.prem.PremEntity
import org.loader.writer.LogWritter
import org.springframework.context.support.ClassPathXmlApplicationContext
object LoaderG {

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

  def load(plat: Plat) = {


    val per = PersonBuilderG.buildPerson(plat)

    val acct = AccountBuilderG.buildAccount(plat)
//
//    val premiseForSaHist = PremiseBuilderG.buildPremise("1",plat.addressF)
//
//    val saHistorical = SaBuilderG.buildSaHistorical(plat,premiseForSaHist)
//    acct.addSaEntity(saHistorical)
//

    //создаем premise для каждого id
    val mPremise:Map[String,PremEntity] =
      plat.potrList.
        groupBy((p)=>p.idObj).
        mapValues((pl) => pl.head).
        mapValues((p)=>PremiseBuilderG.buildPremise(p))

    plat.potrList.map(
      (potr) => {

        val sp = SpBuilderG.build(potr = potr, premise = mPremise(potr.idObj))
        val mtr = MtrBuilderG.build(potr)
        val reg = RegBuilderG.build(mtr = mtr, potr = potr)
        val mtrCfg = MtrConfigBuilderG.build(mtr = mtr,potr = potr)

        val mr = MrBuilderG.build(readDttm = readDttm,
                                  mtrConfig = mtrCfg)
        val regRead = RegReadBuilderG.build(mr,reg,potr.r2)
        RegReadBuilderG.build(mr,reg,potr.r2)

        SpMtrHistBuilderG.build(sp = sp, mtrCfg = mtrCfg)
        val spMtrHist = SpMtrHistBuilderG.build(sp = sp, mtrCfg = mtrCfg)
        MtrLocHistBuilderG.build(mtr = mtr, readDttm)
        SpMtrEvtBuilderG.build(spMtrHist = spMtrHist,mr = mr)


//        val sa = SaBuilderG.buildSaForSp(plat,potr,premiseSp)



        //TODO Если NO_RSCH  = true – то по точке не производим расчет (не привязываем к РДО

        sp
      }
    )


//        //TODO create fake beginning meter read

//        val mr = MrBuilderG.build(mrId = "MR_ID", dt = potr.data, mtrConfig = mtrCfg)
//        val regRead = RegReadBuilderG.build(regReadId = "RR_ID", mr = mr, reg = reg, regReading = potr.r2)
//
//        val premiseSp = PremiseBuilderG.buildPremise("3",potr.address)
//
//        //link mtr_config to sp
//        SpMtrHistBuilderG.build(spMtrHistId = "SP_MTR_H",sp = sp, mtrCfg = mtrCfg)
//
//        val sa = SaBuilderG.buildSaForSp(plat,potr,premiseSp)
//
//        SaSpBuilderG.buildSaSp(saSpId = "SA_SP_A",sa = sa,sp = sp)
//
//        SaSpBuilderG.buildSaSp(saSpId = "SA_SP_H",sa = saHistorical,sp = sp)
//
//        acct.addSaEntity(sa)
//

    PersonBuilderG.addAcctToPer(per,acct)
    generalDAO.save(per)

    LogWritter.log(idPlat = plat.idPlat,perId = per.perId, acctId = acct.acctId)
  }
}
