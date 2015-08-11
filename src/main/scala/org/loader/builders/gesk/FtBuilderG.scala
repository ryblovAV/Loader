package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.pojo.ft.{FtEntity, FtGlEntity}
import org.loader.pojo.sa.SaEntity

object FtBuilderG {

  def buildFtGl(glSeqNbr :Int, dstId: String, amount: Double, totAmtSw: String) = {
    val ftGl = new FtGlEntity

    ftGl.glSeqNbr = glSeqNbr
    ftGl.dstId = dstId
    ftGl.amount = amount
    ftGl.totAmtSw = totAmtSw

    ftGl
  }

  def build(adjId: String, sa: SaEntity, curAmt:Double) = {
    val ft = new FtEntity(KeysBuilder.getEnvId)

    ft.ftId = KeysBuilder.getFtId

    ft.siblingId = adjId
    ft.parentId = "SALDO"
    ft.glDivision = "G-OSN"
    ft.cisDivision = "GESK"
    ft.currencyCd = "RUR"
    ft.ftTypeFlg = "AD"
    ft.curAmt = curAmt
    ft.totAmt = curAmt
    ft.creDttm = LoaderG.activeMonth
    ft.freezeSw = "Y"
    ft.freezeDttm = LoaderG.activeMonth
    ft.arsDt = LoaderG.activeMonth
    ft.correctionSw = "N"
    ft.redundantSw = "N"
    ft.newDebitSw = "N"
    ft.showOnBillSw = "N"
    ft.notInArsSw = "N"
    ft.accountingDt = LoaderG.activeMonth
    ft.xferredOutSw = "N"
    ft.glDistribStatus = "G"
    ft.schedDistribDt = LoaderG.activeMonth
    ft.freezeUserId = "SYSUSER"

    ft.sa = sa
    sa.ftEntitySet.add(ft)

    buildFtGl(glSeqNbr = 10,dstId = "REE 62.1", amount = curAmt,totAmtSw = "N")
    buildFtGl(glSeqNbr = 20,dstId = "SALDO", amount = - curAmt,totAmtSw = "N")

    ft
  }

}
