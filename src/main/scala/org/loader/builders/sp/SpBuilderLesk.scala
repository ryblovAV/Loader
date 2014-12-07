package org.loader.builders.sp

import org.loader.builders.Utills
import org.loader.out.lesk.objects.{ServicePointSourceObject, ServicePointType}
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sp.SpEntity

/**
 * Created by a123 on 04/12/14.
 */
object SpBuilderLesk {

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

  def buildSp(spSource: ServicePointSourceObject, premise:PremEntity) = {

    val sp = new SpEntity(Utills.getEnvId)

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
    sp.spCharEntitySet.add(SpBuilder.buildSpChar(charTypeCd = "NAIM-TP", adhocCharVal = spSource.name))
    sp.spCharEntitySet.add(SpBuilder.buildSpChar(charTypeCd = "ID_TY_1C", adhocCharVal = spSource.id))
    sp.spCharEntitySet.add(SpBuilder.buildSpChar(charTypeCd = "TY_NOM_C", adhocCharVal = spSource.code))


    //    if (spSource.postrOr == "Да") {
    //      spSource.prpotList.filter(_.typeObject == 1).sortWith((a:PrpotSourceObject,b:PrpotSourceObject) => a.effdt before b.effdt)
    //    }



  }

}
