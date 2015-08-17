package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.out.gesk.objects.Per
import org.loader.pojo.billChg.{BChgLineEntity, BchgSqEntity, BillChgEntity}
import org.loader.pojo.sa.SaEntity

object BillChangeBuilderG {


  def build(per:Per, sa:SaEntity) = {

    val descr = s"Перерасчет за предыдущий период (${per.mes}/${per.god}})"

    val billChg = new BillChgEntity(KeysBuilder.getEnvId)

    billChg.billableChgId = KeysBuilder.getBillableChgId
    billChg.sa = sa
    billChg.startDt = per.effdt
    billChg.endDt = DateBuilder.lastDay(per.effdt)
    billChg.descrOnBill = descr
    billChg.billableChgStat = "10"

    //Bill Change Sequence
    val billChgSq = new BchgSqEntity()
    billChgSq.seqNum = 10
    billChgSq.uomCd = "KWH"
    billChgSq.svcQty = per.r

    billChg.billChgSeqEntitySet.add(billChgSq)

    //Bill Change Line
    val billChgLine = new BChgLineEntity()
    billChgLine.lineSeq = 10
    billChgLine.descrOnBill = descr
    billChgLine.chargeAmt = per.nds + per.s
    billChgLine.currencyCd = "RUR"
    billChgLine.showOnBillSw = "Y"
    billChgLine.appInSummSw = "Y"
    billChgLine.dstId = "REE 62.1"
    billChgLine.memoSw = "N"

    billChg.billChgLineEntitySet.add(billChgLine)

    billChg

  }


}
