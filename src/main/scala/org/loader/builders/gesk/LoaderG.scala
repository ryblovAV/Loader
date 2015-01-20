package org.loader.builders.gesk

import org.loader.db.dao.general.GeneralDAO
import org.loader.out.gesk.objects.Plat
import org.springframework.context.support.ClassPathXmlApplicationContext

object LoaderG {

  def load(plat: Plat) = {

    val ctx = new ClassPathXmlApplicationContext("application-context.xml")
    val generalDAO = ctx.getBean(classOf[GeneralDAO])

    val per = PersonBuilderG.buildPerson(plat)
    val acct = AccountBuilderG.buildAccount(plat)
    val acctPer = AccountPersonBuilderG.linkAccoutPerson(per, acct)

    generalDAO.save(per, acct, acctPer)
  }
}
