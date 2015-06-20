package org.loader.builders.general

import org.loader.models.Characteristic
import org.loader.pojo.sp.{SpCharEntity, SpEntity, SpOpAreaEntity}

object SpBuilder {

  implicit def buildChar(char: Characteristic): SpCharEntity = {

    val spChar = new SpCharEntity
    spChar.charTypeCd = char.charTypeCd
    spChar.effdt = char.effDt
    spChar.charVal = char.charVal
    spChar.adhocCharVal = char.adhocCharVal

    spChar
  }

  def addChar(sp: SpEntity,char: Characteristic) = {
    if (!char.isEmpty)
      sp.spCharEntitySet.add(char)
  }

  def addSpOpArea(sp: SpEntity, fsClCd: String, opAreaCd: String) = {
    val spOpArea = new SpOpAreaEntity
    spOpArea.fsClCd = fsClCd
    spOpArea.opAreaCd = opAreaCd

    sp.spOpAreaEntitySet.add(spOpArea)
  }

}
