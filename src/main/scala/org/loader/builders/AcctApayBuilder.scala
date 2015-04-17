package org.loader.builders

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.out.lesk.objects.Client
import org.loader.pojo.acctapay.{AcctApayEntity, AcctApayKEntity}

object AcctApayBuilder {

  def buildAcctApay(client:Client) = {

    val acctApayEntity = new AcctApayEntity(KeysBuilder.getEnvId)

    acctApayEntity.acctApayId = KeysBuilder.getAcctApayId
    acctApayEntity.startDt = DateBuilder.getDefaultDt
    acctApayEntity.endDt = DateBuilder.getDefaultDt
    acctApayEntity.extAcctId = client.currentAccount
    acctApayEntity.entityName = client.codeBank

    //Key
    acctApayEntity.acctApayKEntitySet.add(new AcctApayKEntity(KeysBuilder.getEnvId))

    acctApayEntity
  }
}
