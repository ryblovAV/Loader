package org.loader.builders

import org.loader.db.utl.DBUtl
import org.loader.out.lesk.objects.Client
import org.loader.pojo.acctapay.{AcctApayKEntity, AcctApayEntity}

object AcctApayBuilder {

  def buildAcctApay(client:Client) = {

    val acctApayEntity = new AcctApayEntity(Utills.getEnvId)

    acctApayEntity.acctApayId = Utills.getAcctApayId
    acctApayEntity.startDt = Utills.getDefaultDt
    acctApayEntity.endDt = Utills.getDefaultDt
    acctApayEntity.extAcctId = client.currentAccount
    acctApayEntity.entityName = client.codeBank

    //Key
    acctApayEntity.acctApayKEntitySet.add(new AcctApayKEntity(Utills.getEnvId))

    acctApayEntity
  }
}
