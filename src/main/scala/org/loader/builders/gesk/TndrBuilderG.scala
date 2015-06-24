package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.pojo.tndr._

object TndrBuilderG {


  def buildPayTndr(depCtlStPk: DepCtlStPk,
                   extBatchId: String,
                   tenderAmt: Double,
                   accountDt: java.util.Date,
                   acctId: String) = {

    val extReferenceId = KeysBuilder.getExtReferenceId

    val payTndtStPk = new PayTndrStPk(depCtlStPk,extBatchId,extReferenceId)
    val payTndrSt = new PayTndrStEntity(payTndtStPk)

    payTndrSt.payTndStgStFlg = "10"
    payTndrSt.tenderAmt = tenderAmt
    payTndrSt.accountingDt = accountDt
    payTndrSt.tenderTypeCd = "KVIT"
    payTndrSt.custId = acctId

    payTndrSt
  }

  def buildTndr(depCtlStPk: DepCtlStPk, sumPayAmt: Double, cntPay: Int) = {

    val extBatchId = KeysBuilder.getExtBatchId
    val tndrCtlStPk = new TndrCtlStPk(depCtlStPk,extBatchId)

    val tndrCtlSt = new TndrCtlStEntity(tndrCtlStPk)
    tndrCtlSt.tndCtlStgStFlg = "20"
    tndrCtlSt.totTndrAmt = sumPayAmt
    tndrCtlSt.totTndrCnt = cntPay

    tndrCtlSt
  }

  def buildDept(dt: java.util.Date, sumPayAmt: Double) = {
    val extTransmitId = KeysBuilder.getExtTransmitId
    val deptCtlStPk = new DepCtlStPk("ISTOR",extTransmitId)
    val deptCtlSt = new DepCtlStEntity(deptCtlStPk)

    deptCtlSt.depCtlStgStFlg = "20"
    deptCtlSt.transmitDttm = dt
    deptCtlSt.currencyCd = "RUR"
    deptCtlSt.totTndrCtlAmt = sumPayAmt
    deptCtlSt.totTndrCtlCnt = 1

    deptCtlSt
  }

}
