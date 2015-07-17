package org.loader.builders.gesk

import grizzled.slf4j.Logging
import org.loader.builders.general.{DateBuilder, Constants, KeysBuilder, SaBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.{Plat, Potr}
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sa.SaEntity
object SaBuilderG extends Logging{

  val mTranslateKategory = Map("1 цен.кат." -> "1")

  def translateCategory(prim:String):String = mTranslateKategory.get(prim) match {
    //TODO добавить остальные значения
    case Some(v) => v
    case _ => throw new Exception(s"Couldn't decode KATEGORY -> $prim")
  }

  def checkGr(gr:String):Boolean = (gr == "210") || (gr == "2010")

  def translateNaprayg(optSn: Option[String], optGr: Option[String]):Option[String] = {
    (optSn, optGr) match {
      case (Some(sn),_) if sn == "нн" => Some("0.4")
      case (_,Some(gr)) if checkGr(gr) => Some("110")
      case (Some(sn),Some(gr)) if (sn == "сн") && (!checkGr(gr)) => Some("6")
      case _ => None
    }
  }

  def buildSaForSp(plat: Plat, potr: Potr, charPrem: PremEntity) = {

    val sa = new SaEntity(KeysBuilder.getEnvId)

    sa.saId = KeysBuilder.getSaId
    sa.saTypeCd = if (potr.isInterval) "L_EL_INT" else GeskConstants.saCommercialTypeCd
    sa.startDt = LoaderG.activeMonth
    sa.saStatusFlg = Constants.saOpenStatus
    for (kOkwed <- plat.kOkwed) {
      sa.sicCd = kOkwed.split(",|;| ").head.split(" ").head
      if (sa.sicCd.length > 8)
        info(s"kOkwed = $kOkwed, sicCd = ${sa.sicCd}}")
        sa.sicCd = " "
    }
    sa.cisDivision = GeskConstants.cisDivision
    sa.charPrem = charPrem

    if (potr.isInterval) {
      sa.ibSaCutoffTm = DateBuilder.getDate(year = 1900,month = 1,day = 1,hour = 23,minute = 0)
      sa.ibBaseTmDayFlg = "P"
    }

    //максимальная мощность
    for (ustM <- potr.mt.ustM)
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "M-POWER",adhocCharVal = ustM.toString))

    //TODO ценовая категория
//    for (prim <- potr.tar.prim)
//      SaBuilder.addChar(sa,Characteristic(charTypeCd = "KATEGORI",charVal = translateCategory(prim = prim)))

    //Группа населения
    for (grpt46 <- potr.grpt46)
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "GRPTR-46",charVal = grpt46))

    //диапазон напряжения
    for (naprayg <- translateNaprayg(optSn = potr.tar.sn,optGr = potr.tar.gr))
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "NAPRAYG2",charVal = naprayg))

    //NEPREMEN
    for (t <- potr.t if (t.take(1) == "8") || (t.take(1) == "9"))
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "NEPREMEN",charVal = "NEPREMEN"))

    //PRIMEN
    SaBuilder.addChar(sa,Characteristic(charTypeCd = "PRIMEN",charVal = "PRIMEN"))

    //PRICE-1
    for (znj <- potr.tar.znJ)
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "PRICE-1",charVal = znj))

    sa
  }

}
