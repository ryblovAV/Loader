package org.loader.builders.general

import org.loader.models.Characteristic
import org.loader.pojo.sa.{SaCharEntity, SaEntity}
import scala.language.implicitConversions

object SaBuilder {

  implicit def buildChar(char: Characteristic): SaCharEntity = {

    val saChar = new SaCharEntity()
    saChar.charTypeCd = char.charTypeCd
    saChar.effdt = char.effDt
    saChar.charVal = char.charVal
    saChar.adhocCharVal = char.adhocCharVal

    saChar
  }

  def addChar(sa: SaEntity,char: Characteristic) = {
    if (!char.isEmpty)
      sa.saCharEntitySet.add(char)
  }

}
