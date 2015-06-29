package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.mtrcfg.MtrConfigEntity

object MtrConfigBuilderG {

  def build(mtr: MtrEntity,
            potr: Potr): MtrConfigEntity = {
    val mtrConfig = new MtrConfigEntity(KeysBuilder.getEnvId)

    mtrConfig.mtrConfigId = KeysBuilder.getMtrConfigId

    mtrConfig.mtr = mtr
    mtrConfig.effDttm = mtr.receiveDt

    if (potr.isMultiZone)
      mtrConfig.mtrConfigTyCd = "EE-1"
    else
      mtrConfig.mtrConfigTyCd = "EE-ZS"

    mtrConfig
  }

}
