package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.reg.RegEntity
import org.loader.pojo.regread.RegReadEntity

object RegReadBuilderG {

  def build(mr: MrEntity, reg: RegEntity, regReading: Double):RegReadEntity = {
    val rr = new RegReadEntity(KeysBuilder.getEnvId)

    rr.regReadId = KeysBuilder.getRegReadId
    rr.mr = mr
    rr.reg = reg
    rr.regReading = regReading
    rr.readTypeFlg = "50"
    rr.readSeq = 1

    rr.mr.regReadMrEntitySet.add(rr)

    rr
  }

}
