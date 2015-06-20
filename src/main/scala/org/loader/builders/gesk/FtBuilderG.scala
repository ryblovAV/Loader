package org.loader.builders.gesk

import java.util.Calendar

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.db.utl.DBUtl
import org.loader.pojo.ft.{FtGlEntity, FtEntity}
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
    ft.parentId = "OLDBAL"
    ft.ftTypeFlg = "AD"
    ft.curAmt = curAmt
    ft.totAmt = curAmt
    ft.creDttm = Calendar.getInstance().getTime
    ft.arsDt = DateBuilder.lastDay(DateBuilder.addMonth(LoaderG.activeMonth,-1))
    ft.accountingDt = LoaderG.activeMonth

    ft.schedDistribDt = DateBuilder.trunc(Calendar.getInstance()).getTime

    ft.sa = sa
    sa.ftEntitySet.add(ft)

    buildFtGl(glSeqNbr = 1,dstId = "REE 62.1", amount = curAmt,totAmtSw = "Y")
    buildFtGl(glSeqNbr = 2,dstId = "TRNSF-KORR", amount = - curAmt,totAmtSw = "N")

    ft
  }

}
