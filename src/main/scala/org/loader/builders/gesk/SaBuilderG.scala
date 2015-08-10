package org.loader.builders.gesk

import grizzled.slf4j.Logging
import org.loader.builders.general.{DateBuilder, Constants, KeysBuilder, SaBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.{Tar, Plat, Potr}
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sa.SaEntity

object SaBuilderG extends Logging {

  val mTranslateKategory = Map("1 цен.кат." -> "1")

  def checkGr(gr:String):Boolean = (gr == "210") || (gr == "2010")

  def translateNaprayg(optSn: Option[String], optGr: Option[String]):Option[String] = {
    (optSn, optGr) match {
      case (Some(sn),_) if sn == "нн" => Some("0.4")
      case (_,Some(gr)) if checkGr(gr) => Some("110")
      case (Some(sn),Some(gr)) if (sn == "сн") && (!checkGr(gr)) => Some("6")
      case _ => None
    }
  }

  def checkCkNet(cK:String) = cK == "-без УПП и СбН=для сетевых"

  def translatePriceCategory(cK:String) = cK match {
    case s:String if checkCkNet(s) => Some("8")
    case s if s.take(1) == "-" => None
    case s:String => Some(s)
    case _ => None
  }

  def checkTarSw1(tar:Tar) =
      (!tar.cSw1.isEmpty) &&
      (!tar.cSw1Sr.isEmpty) &&
      (!tar.cSw1Us.isEmpty) &&
      (!tar.cSw1N.isEmpty) &&
      (!tar.cSw1In.isEmpty)

  def translateRS(potr:Potr) = {
    if (checkCkNet(potr.tar.cK.getOrElse("")))
      Some("G_TSOLOS")
    else if ((potr.tar.prim.getOrElse("").take(1) == "1") && checkTarSw1(potr.tar))
      Some("G_ORG_1K")
    else if (potr.k1.getOrElse(0) != 0)
      Some("G_VOLMAX")
    else if ((potr.tar.prim.getOrElse("").take(1) == "1") && checkTarSw1(potr.tar) && (potr.tar.cSw1Us.get == 0))
      Some("G_UR_1KP")
    else if ((potr.tar.prim.getOrElse("").take(2) == "2") && checkTarSw1(potr.tar))
      Some("G_OR_ZON")
    else if (potr.isInterval)
      Some("L_KOR_S")
    else if (!potr.tar.c.isEmpty)
      Some("G_NASPUN")
    else None


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
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "M-POWER",adhocCharVal = ustM.toString, effDt = sa.startDt))

    //Ценовая категория
    for (cK <- potr.tar.cK)
      for (charVal <- translatePriceCategory(cK))
        SaBuilder.addChar(sa,Characteristic(charTypeCd = "KATEGORI",charVal = charVal, effDt = sa.startDt))

    //Группа населения
    for (grpt46 <- potr.grpt46)
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "GRPTR-46",charVal = grpt46, effDt = sa.startDt))

    //диапазон напряжения
    for (naprayg <- translateNaprayg(optSn = potr.tar.sn,optGr = potr.tar.gr))
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "NAPRAYG2",charVal = naprayg, effDt = sa.startDt))

    //NEPREMEN
    for (t <- potr.t if (t.take(1) == "8") || (t.take(1) == "9"))
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "NEPREMEN",charVal = "NEPREMEN", effDt = sa.startDt))

    //PRIMEN
    SaBuilder.addChar(sa,Characteristic(charTypeCd = "PRIMEN",charVal = "PRIMEN", effDt = sa.startDt))

    //PRICE-1
    for (znj <- potr.tar.znJ)
      SaBuilder.addChar(sa,Characteristic(charTypeCd = "PRICE-1",charVal = znj, effDt = sa.startDt))

    //RS
    for (rsCd <- translateRS(potr))
      SaBuilder.addRsHist(sa = sa,rsCd = rsCd)

    sa
  }

}
