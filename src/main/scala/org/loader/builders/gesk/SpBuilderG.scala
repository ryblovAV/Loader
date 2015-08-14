package org.loader.builders.gesk

import java.util.Date

import org.loader.builders.general.{DateBuilder, KeysBuilder, SpBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sp.{SpCharEntity, SpEntity}

import org.loader.builders.general.BuilderUtl._
import scala.language.implicitConversions


object SpBuilderG {

  private def buildSpChar(charTypeCd:String, charVal:String = " ", adhocCharVal:String = " ", effDt:Date = DateBuilder.getDefaultDt) = {
    val spChar = new SpCharEntity()

    spChar.charTypeCd = charTypeCd
    spChar.charVal = charVal
    spChar.adhocCharVal = adhocCharVal
    spChar.effdt = effDt

    spChar
  }

  def addMkd(potr: Potr):String  = if (potr.isMkd) {
    potr.mt.mkd match {
      case Some(mkd) => s",mkd = $mkd"
      case None      => ""
    }
  } else ""

  implicit def defaultForTr(p:Option[Double]):String = translateToString(p.getOrElse(0))

  def build(potr:Potr, premise:PremEntity, isOrphan: Boolean):SpEntity = {
    val sp = new SpEntity(KeysBuilder.getEnvId)

    sp.spId = KeysBuilder.getSpId
    sp.spTypeCd = "E-URTU"
    sp.installDt = potr.mt.dataSh.getOrElse(LoaderG.activeMonth)
    sp.spSrcStatusFlg = "C"

    sp.mtrLocDetails =
      (if (isOrphan) s"${potr.naimp} ${potr.address.abv2} ${potr.address.ul} ${potr.address.abv1} ${potr.address.dom} "
       else potr.naimp) + addMkd(potr)

    //Наименование ТУ
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "NAIM-TP", adhocCharVal = potr.naimp, effDt = sp.installDt))

    //Номер позиции ТУ из прежней системы
    for (kp <- potr.kp)
      SpBuilder.addChar(sp,Characteristic(charTypeCd = "TY_NOM_C", adhocCharVal = kp, effDt = sp.installDt))

    //ID ТУ из прежней системы (юр.лица)
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "ID_TY_1C", adhocCharVal = potr.idRec, effDt = sp.installDt))

    //потери
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_LIN",charVal = potr.mt.pLi, effDt = sp.installDt))
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_TR",charVal = potr.mt.pTr, effDt = sp.installDt))
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_TSO",charVal = potr.k1, effDt = sp.installDt))

    SpBuilder.addChar(sp,Characteristic(charTypeCd = "ISKL_M",adhocCharVal = "0", effDt = sp.installDt))
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "CI_GENCP",charVal = "Y", effDt = sp.installDt))
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_A",charVal = "0", effDt = sp.installDt))
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_B",charVal = "0", effDt = sp.installDt))
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_EXT",charVal = "0", effDt = sp.installDt))


    //участки обслуживания
    for (fsClCd <- Array("KON_P","MONTA","OGRAN","OPLOM","POKAZ","PRIBO", "KONTR")) {
      SpBuilder.addSpOpArea(sp,fsClCd,"GESK_YO")
    }

    //ИД ТУ из БД граждан ГЭСК
    val idPhysic = (potr.parent.chGuk, potr.parent.iChS) match {
      case (Some("*"),_) => Some("Не найден идентификатор физ.лица ГЭСК")
      case (_,Some(iChS)) => Some(iChS)
      case _ => None
    }

    for (adhocCharVal <- idPhysic) {
      SpBuilder.addChar(sp,Characteristic(charTypeCd = "ID_TY_GE", adhocCharVal = adhocCharVal, effDt = sp.installDt))
    }

    sp.prem = premise

    sp
  }
}
