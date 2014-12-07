package org.loader.builders

import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity

/**
 * Created by a123 on 25/11/14.
 */
object AccountPersonBuilder {
  def linkAccoutPerson(per:PerEntity, acct:AcctEntity) = {

    val acctPer = new AcctPerEntity(per,acct)
    acctPer.billAddrSrceFlg = LeskConstants.billAddrSrceFlg
    acctPer
  }
}
