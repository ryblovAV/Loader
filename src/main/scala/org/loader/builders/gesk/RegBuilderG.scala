package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.reg.RegEntity

object RegBuilderG {

  def build(mtr: MtrEntity, potr: Potr, isMultiZone: Boolean, seq: Int):RegEntity = {
    val reg = new RegEntity(KeysBuilder.getEnvId)

    reg.regId = KeysBuilder.getRegId

    reg.mtr = mtr
    reg.effDttm = mtr.receiveDt

    if (isMultiZone) {
      reg.touCd = potr.zone.iZn.get
    }
    reg.readSeq = seq

    reg.consumSubFlg = if (potr.isHistVol) "C" else "S"
    reg.regConst = potr.mt.rks
    reg.readOutTypeCd = "ELT"

    reg.nbrOfDgtsLft = 7
    reg.nbrOfDgtsRgt = 3
    reg.fullScale = 9999999.999

    reg.howToUseFlg = "+"
    reg.uomCd = "KWH"

    mtr.regMtrEntitySet.add(reg)

    reg
  }

}
