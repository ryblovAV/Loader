package org.loader.builders.gesk

import org.loader.builders.general.DateBuilder
import org.loader.db.dao.general.GeneralDAO
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sasp.SaSpEntity
import org.springframework.context.support.ClassPathXmlApplicationContext
object LoaderG {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val generalDAO = ctx.getBean(classOf[GeneralDAO])

  val startActiveDt = DateBuilder.getDate(2015,2,1)
  val stopHistoricalDt = DateBuilder.addMinute(startActiveDt,-1)

  def findPer(perId: String) = {
    generalDAO.findPer(perId)
  }

  def removePer(perId: String) = {
    generalDAO.removePer(perId)
  }

  def load(plat: Plat) = {


    val per = PersonBuilderG.buildPerson(plat)

    val acct = AccountBuilderG.buildAccount(plat)

    val premiseForSaHist = PremiseBuilderG.buildPremise("1",plat.addressF)

    val saHistorical = SaBuilderG.buildSaHistorical(plat,premiseForSaHist)
    acct.addSaEntity(saHistorical)

    plat.potrList.map(
      (potr) => {

        //TODO create meter

        //TODO create fake beginning meter read

        val premiseSp = PremiseBuilderG.buildPremise("3",potr.address)
        val sp = SpBuilderG.build(potr,premiseSp)

//        val premiseSa = PremiseBuilderG.buildPremise("2",potr.address)
        val sa = SaBuilderG.buildSaForSp(plat,potr,premiseSp)

        SaSpBuilderG.buildSaSp("SA_SP_A",sa,sp)

        SaSpBuilderG.buildSaSp("SA_SP_H",saHistorical,sp)

        acct.addSaEntity(sa)
      }
    )

    PersonBuilderG.addAcctToPer(per,acct)

    generalDAO.save(per)
  }
}
