package org.loader.builders

import org.loader.out.lesk.objects.Client
import org.loader.pojo.acctapay.{AcctApayEntity, AcctApayKEntity}

object AcctApayBuilder {

  def buildAcctApay(client:Client) = {

    val acctApayEntity = new AcctApayEntity(Utils.getEnvId)

    acctApayEntity.acctApayId = Utils.getAcctApayId
    acctApayEntity.startDt = Utils.getDefaultDt
    acctApayEntity.endDt = Utils.getDefaultDt
    acctApayEntity.extAcctId = client.currentAccount
    acctApayEntity.entityName = client.codeBank

    //Key
    acctApayEntity.acctApayKEntitySet.add(new AcctApayKEntity(Utils.getEnvId))

    acctApayEntity
  }
}
