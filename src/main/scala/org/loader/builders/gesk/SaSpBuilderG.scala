package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sasp.SaSpEntity
import org.loader.pojo.sp.SpEntity

object SaSpBuilderG {

  def buildSaSp(sa: SaEntity, sp: SpEntity, mr: MrEntity, isMinus: Boolean) = {

    val sasp = new SaSpEntity(
      KeysBuilder.getEnvId,
      KeysBuilder.getSaSpId,
      sa,
      sp)

    sasp.startDttm = DateBuilder.addMinute(sa.startDt,1)
    sasp.stopDttm  = if (sa.endDt != null) DateBuilder.addMinute(sa.endDt,-1) else sa.endDt

    sasp.startMr = mr

    sasp.usageFlg = if (isMinus) "-" else "+"
    sasp.usePct = 100

    sasp

  }
}
