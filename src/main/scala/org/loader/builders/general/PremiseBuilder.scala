package org.loader.builders.general

import org.loader.models.Characteristic
import org.loader.pojo.prem.{PremCharEntity, PremEntity}

object PremiseBuilder {

  implicit def buildPremiseChar(char: Characteristic): PremCharEntity = {

    val premiseChar = new PremCharEntity()
    premiseChar.charTypeCd = char.charTypeCd
    premiseChar.effdt = char.effDt
    premiseChar.charVal = char.charVal
    premiseChar.adhocCharVal = char.adhocCharVal

    premiseChar
  }

  def addChar(premise: PremEntity,
              char: Characteristic) = {
    if (!char.isEmpty)
      premise.premCharEntitySet.add(char)
  }

}
