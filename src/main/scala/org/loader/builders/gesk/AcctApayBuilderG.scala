package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctapay.{AcctApayKEntity, AcctApayEntity}

object AcctApayBuilderG {

  def buildAcctApay(plat: Plat, acct: AcctEntity):Unit = {

    for {
      rspl <- plat.rspl
      bankNb <- plat.bankNb
    } {

      val acctApayEntity = new AcctApayEntity(KeysBuilder.getEnvId)

      acctApayEntity.acctApayId = KeysBuilder.getAcctApayId
      acctApayEntity.account = acct
      acctApayEntity.startDt = DateBuilder.getDefaultDt
      acctApayEntity.endDt = DateBuilder.getDefaultDt
      acctApayEntity.extAcctId = rspl
      acctApayEntity.entityName = bankNb

      acct.acctApayEntitySet.add(acctApayEntity)

    }

  }

}
