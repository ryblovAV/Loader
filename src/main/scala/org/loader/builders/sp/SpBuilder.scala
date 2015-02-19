package org.loader.builders.sp

import java.util.Date

import org.loader.builders.Utils
import org.loader.out.lesk.objects.{ServicePointSourceObject, ServicePointType}
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sp.{SpCharEntity, SpEntity}

object SpBuilder {

  def defineType(spSource: ServicePointSourceObject) = {
    spSource.getType match {
      case ServicePointType(true, _, _) => "L_LOSS"
      case ServicePointType(_, true, _) => "HIST_VOL"
      case ServicePointType(_, _, true) => "EGSHIL_O"
      case _ => "E-URTU"
    }
  }

  def defineStatus(spSource: ServicePointSourceObject) = {
    if (spSource.isClose) "D" else "C"
  }

  def buildSpChar(charTypeCd:String, charVal:String = " ", adhocCharVal:String = " ", effDt:Date = Utils.getDefaultDt) = {
    val spChar = new SpCharEntity()

     spChar.charTypeCd = charTypeCd
     spChar.charVal = charVal
     spChar.adhocCharVal = adhocCharVal
     spChar.effdt = effDt

    spChar
  }

  def buildSp(spSource: ServicePointSourceObject, premise:PremEntity) = {

    val sp = new SpEntity(Utils.getEnvId)

    sp.spTypeCd = defineType(spSource)
    sp.prem = premise
    sp.installDt = spSource.installDt

    val substation = spSource.substation
    sp.facLvl1Cd = substation.level1
    sp.facLvl2Cd = substation.level2
    sp.facLvl3Cd = substation.level3

    sp.mtrLocDetails = spSource.name
    sp.abolishDt = spSource.abolishDt
    sp.spSrcStatusFlg = defineStatus(spSource)

    //Chars
    sp.spCharEntitySet.add(buildSpChar(charTypeCd = "NAIM-TP", adhocCharVal = spSource.name))
    sp.spCharEntitySet.add(buildSpChar(charTypeCd = "ID_TY_1C", adhocCharVal = spSource.id))
    sp.spCharEntitySet.add(buildSpChar(charTypeCd = "TY_NOM_C", adhocCharVal = spSource.code))


//    if (spSource.postrOr == "Да") {
//      spSource.prpotList.filter(_.typeObject == 1).sortWith((a:PrpotSourceObject,b:PrpotSourceObject) => a.effdt before b.effdt)
//    }



  }
}
