package org.loader.builders.gesk

import org.loader.pojo.mr.MrEntity
import org.loader.pojo.spmtrevt.SpMtrEvtEntity
import org.loader.pojo.spmtrhist.SpMtrHistEntity

object SpMtrEvtBuilderG {
  def build(spMtrHist: SpMtrHistEntity, mr: MrEntity) = {
    val spMtrEvt = new SpMtrEvtEntity

    spMtrEvt.seqno = 0
    spMtrEvt.mtrOnOffFlg = "1"
    spMtrEvt.spMtrEvtFlg = "I"
    spMtrEvt.mr = mr

    spMtrHist.spMtrEvtSpMtrHistEntitySet.add(spMtrEvt)

    spMtrEvt
  }
}
