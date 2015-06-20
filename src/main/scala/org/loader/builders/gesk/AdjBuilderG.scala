package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.pojo.adj.AdjEntity
import org.loader.pojo.sa.SaEntity

object AdjBuilderG {

  def build(sa: SaEntity, adjAmt: Double) = {
    val adj = new AdjEntity(KeysBuilder.getEnvId)

    adj.adjId = KeysBuilder.getAdjId
    adj.sa = sa
    adj.adjTypeCd = "SALDO"
    adj.adjStatusFlg = "50"
    adj.adjAmt = adjAmt
    adj.currencyCd = "RUR"
    adj.comments = "Задолженность Юридические лица"

    sa.adjEntitySet.add(adj)

    adj
  }

}
