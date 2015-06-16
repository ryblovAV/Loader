package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.pojo.mtrcfg.MtrConfigEntity
import org.loader.pojo.sp.SpEntity
import org.loader.pojo.spmtrhist.SpMtrHistEntity

object SpMtrHistBuilderG {

  def build(sp: SpEntity, mtrCfg: MtrConfigEntity):SpMtrHistEntity = {
    val spMtrHist = new SpMtrHistEntity(KeysBuilder.getEnvId)

    spMtrHist.spMtrHistId = KeysBuilder.getSpMtrHistId
    spMtrHist.sp = sp
    spMtrHist.mtrCfg = mtrCfg

    sp.spMtrHistEntity.add(spMtrHist)

    spMtrHist
  }

}
