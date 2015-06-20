package org.loader.builders.general

import org.loader.models.Characteristic
import org.loader.pojo.mtr.{MtrCharEntity, MtrEntity}

object MtrBuilder {
  
  implicit def buildChar(char: Characteristic): MtrCharEntity = {

    val mtrChar = new MtrCharEntity
    mtrChar.charTypeCd = char.charTypeCd
    mtrChar.effdt = char.effDt
    mtrChar.charVal = char.charVal
    mtrChar.adhocCharVal = char.adhocCharVal

    mtrChar
  }

  def addChar(mtr: MtrEntity,char: Characteristic) = {
    if (!char.isEmpty)
      mtr.mtrCharEntitySet.add(char)
  }
}
