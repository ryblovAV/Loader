package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sasp.SaSpEntity
import org.loader.pojo.sp.SpEntity

object SaSpBuilderG {

  def buildSaSp(saSpId: String, sa: SaEntity, sp: SpEntity) = {

    val sasp = new SaSpEntity(KeysBuilder.getEnvId,saSpId,sa,sp)

    sasp.startDttm = DateBuilder.addMinute(sa.startDt,1)
    sasp.stopDttm  = if (sa.endDt != null) DateBuilder.addMinute(sa.endDt,-1) else sa.endDt

    sasp.usageFlg = "Y"
    sasp.usePct = 100

    sasp

  }
}
