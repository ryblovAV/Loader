package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.mtrcfg.MtrConfigEntity

object MrBuilderG {

  def build(dt: java.util.Date, mtrConfig: MtrConfigEntity):MrEntity = {
    val mr = new MrEntity(KeysBuilder.getEnvId)

    mr.mrId = KeysBuilder.getMrId

    mr.readDttm = dt
    mr.useOnBillSw = "Y"
    mr.mtrConfig = mtrConfig

    mr.mtrConfig.mrMtrConfigEntitySet.add(mr)

    mr
  }

}
