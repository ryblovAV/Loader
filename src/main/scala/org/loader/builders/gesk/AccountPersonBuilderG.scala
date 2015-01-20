package org.loader.builders.gesk

import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity

object AccountPersonBuilderG {

  def linkAccoutPerson(per: PerEntity, acct: AcctEntity) = {

    val acctPer = new AcctPerEntity(per, acct)

    acctPer
  }
}
