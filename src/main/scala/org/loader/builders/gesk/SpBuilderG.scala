package org.loader.builders.gesk

import java.util.Date
import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sp.{SpCharEntity, SpEntity}

object SpBuilderG {

  private def buildSpChar(charTypeCd:String, charVal:String = " ", adhocCharVal:String = " ", effDt:Date = DateBuilder.getDefaultDt) = {
    val spChar = new SpCharEntity()

    spChar.charTypeCd = charTypeCd
    spChar.charVal = charVal
    spChar.adhocCharVal = adhocCharVal
    spChar.effdt = effDt

    spChar
  }

  def build(potr:Potr, premise:PremEntity):SpEntity = {
    val sp = new SpEntity(KeysBuilder.getEnvId)

    sp.spId = KeysBuilder.getSpId
    sp.installDt = DateBuilder.getDefaultDt
    sp.spSrcStatusFlg = "C"
    sp.mtrLocDetails = potr.naimp

    sp.spCharEntitySet.add(buildSpChar(charTypeCd = "NAIM-TP",adhocCharVal = potr.naimp))
    sp.spCharEntitySet.add(buildSpChar(charTypeCd = "TY_NOM_C",adhocCharVal = potr.kelsch))

    sp.prem = premise

    sp
  }
}
