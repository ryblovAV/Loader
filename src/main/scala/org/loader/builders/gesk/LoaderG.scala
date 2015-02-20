package org.loader.builders.gesk

import org.loader.db.dao.general.GeneralDAO
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.sa.SaEntity
import org.springframework.context.support.ClassPathXmlApplicationContext

object LoaderG {

  def load(plat: Plat) = {

    val ctx = new ClassPathXmlApplicationContext("application-context.xml")
    val generalDAO = ctx.getBean(classOf[GeneralDAO])

    val per = PersonBuilderG.buildPerson(plat)

    val acct = AccountBuilderG.buildAccount(plat)

    val premise = PremiseBuilderG.buildPremise(plat.addressF)

    val saHistorical = SaBuilderG.buildSaHistorical(plat)
    saHistorical.premise = premise
    acct.addSaEntity(saHistorical)

    val spList = plat.potrList.map(
      (potr) => {
        val premise = PremiseBuilderG.buildPremise(potr.address)
        val sp = SpBuilderG.build(potr,premise)
        sp.prem = premise
      }
    )

//    val sasp = Sa

    val acctPer = AccountPersonBuilderG.linkAccoutPerson(per, acct)

    generalDAO.save(per, acct, acctPer)
  }
}
