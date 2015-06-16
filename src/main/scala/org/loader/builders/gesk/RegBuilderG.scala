package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.reg.RegEntity

object RegBuilderG {

  //TODO add multi zone
  def build(mtr: MtrEntity, potr: Potr):RegEntity = {
    val reg = new RegEntity(KeysBuilder.getEnvId)

    reg.regId = KeysBuilder.getRegId

    reg.mtr = mtr
    reg.effDttm = mtr.receiveDt
    reg.readSeq = 1
    reg.consumSubFlg = "C"
    reg.regConst = potr.rks
    reg.readOutTypeCd = "ELT"

    reg.nbrOfDgtsLft = 7
    reg.nbrOfDgtsRgt = 3
    reg.fullScale = 9999999.999

    mtr.regMtrEntitySet.add(reg)

    reg

  }

}
