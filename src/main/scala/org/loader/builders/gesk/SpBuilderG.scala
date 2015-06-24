package org.loader.builders.gesk

import java.util.Date

import org.loader.builders.general.{DateBuilder, KeysBuilder, SpBuilder}
import org.loader.models.Characteristic
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
    sp.spTypeCd = "E-URTU"
    sp.installDt = potr.mt.dataSh.getOrElse(DateBuilder.getDefaultDt)
    sp.spSrcStatusFlg = "C"
    sp.mtrLocDetails = potr.naimp

    //Наименование ТУ
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "NAIM-TP", adhocCharVal = potr.naimp))

    //Номер позиции ТУ из прежней системы
    for (kp <- potr.kp)
      SpBuilder.addChar(sp,Characteristic(charTypeCd = "TY_NOM_C", adhocCharVal = kp))

    //ID ТУ из прежней системы (юр.лица)
    SpBuilder.addChar(sp,Characteristic(charTypeCd = "ID_TY_1C", adhocCharVal = potr.idRec))

    //потери
    for (pLi <- potr.mt.pLi)
      SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_LIN",adhocCharVal = pLi))
    for (pTr <- potr.mt.pTr)
      SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_TR",adhocCharVal = pTr))
    for (k1 <- potr.k1)
      SpBuilder.addChar(sp,Characteristic(charTypeCd = "LOSS_TSO",adhocCharVal = k1))

    //участки обслуживания
    for (fsClCd <- Array("KON_P","MONTA","OGRAN","OPLOM","POKAZ","PRIBO", "KONTR")) {
      SpBuilder.addSpOpArea(sp,fsClCd,"GESK_YO")
    }

    sp.prem = premise

    sp
  }
}
