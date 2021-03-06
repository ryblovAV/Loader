package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sasp.SaSpEntity
import org.loader.pojo.sp.SpEntity

object SaSpBuilderG {

  def buildSaSp(sa: SaEntity, sp: SpEntity, mr: Option[MrEntity], isMinus: Boolean) = {

    //correct sa.start_dt
    if (sa.startDt.before(sp.installDt))
      sa.startDt = sp.installDt

    val sasp = new SaSpEntity(
      KeysBuilder.getEnvId,
      KeysBuilder.getSaSpId,
      sa,
      sp
    )

    sasp.startDttm = DateBuilder.addMinute(sa.startDt,1)
    sasp.stopDttm  = if (sa.endDt != null) DateBuilder.addMinute(sa.endDt,-1) else sa.endDt

    for (startMr <- mr)
      sasp.startMr = startMr

    sasp.usageFlg = if (isMinus) "-" else "+"

    //TODO load opla
    sasp.usePct = 100 //opla


    sasp
  }

}
