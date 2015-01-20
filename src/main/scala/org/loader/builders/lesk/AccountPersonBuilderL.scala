package org.loader.builders.lesk

import org.loader.builders.LeskConstants
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity

object AccountPersonBuilderL {
  def linkAccoutPerson(per: PerEntity, acct: AcctEntity) = {

    val acctPer = new AcctPerEntity(per, acct)
    acctPer.billAddrSrceFlg = LeskConstants.billAddrSrceFlg
    acctPer
  }
}
