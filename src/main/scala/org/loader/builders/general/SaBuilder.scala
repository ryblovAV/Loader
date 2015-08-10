package org.loader.builders.general

import org.loader.models.Characteristic
import org.loader.pojo.sa.{SaRsHistEntity, SaCharEntity, SaEntity}
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

  def addRsHist(sa: SaEntity, rsCd: String) = {
    val saRsHist = new SaRsHistEntity()
    saRsHist.effdt = sa.startDt
    saRsHist.rsCd = rsCd

    sa.saRsHistEntitySet.add(saRsHist)
  }

}
