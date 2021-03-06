package org.loader.builders.gesk

import org.loader.builders.general.KeysBuilder
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.reg.RegEntity

object RegBuilderG {

  def build(mtr: MtrEntity, potr: Potr, isMultiZone: Boolean, isHistVol: Boolean, seq: Int):RegEntity = {
    val reg = new RegEntity(KeysBuilder.getEnvId)

    reg.regId = KeysBuilder.getRegId

    reg.mtr = mtr
    reg.effDttm = mtr.receiveDt

    if (isMultiZone) {
      reg.touCd = potr.zone.iZn.get
    }
    reg.readSeq = seq

    reg.consumSubFlg = if ((isHistVol) || (potr.isInterval)) "C" else "S"
    reg.regConst = if (potr.isInterval) 1 else potr.mt.rks
    reg.readOutTypeCd = "ELT"

    reg.nbrOfDgtsLft = 8
    reg.nbrOfDgtsRgt = 3
    reg.fullScale = 99999999.999

    reg.howToUseFlg = "+"
    reg.uomCd = "KWH"

    mtr.regMtrEntitySet.add(reg)

    reg
  }

}
