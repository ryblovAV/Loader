package org.loader.builders.gesk

import org.loader.builders.general.BuilderUtl._
import org.loader.builders.general.DateBuilder._
import org.loader.builders.general.{DateBuilder, KeysBuilder, MtrBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.mtr.MtrEntity


object MtrBuilderG {

  //перечень допустимых значений
  val voltValid = List("110","220","380")
  val klastValid = List("0.5","1","2")

  def filterAccuracyClass(klastOpt: Option[String]):Option[String] = klastOpt match {
    case Some(klast) if (klastValid.contains(klast)) => Some(klast)
    case _ => None
  }

  def getRecieveDt(potr: Potr):java.util.Date = potr.mt.dataSh match {
    case Some(dt) => dt
    case None => potr.getDateFromGw match {
      case Some(dtGw) => dtGw
      case None => DateBuilder.getDefaultDt
    }
  }

  def getCheckDt(potr: Potr):Option[String] = potr.mt.dp match {
    case Some(dp) => Some(dp)
    case None => potr.getDateFromGw.map(dateToStr(_))
  }

  def build(potr: Potr) = {

    val mtr = new MtrEntity(KeysBuilder.getEnvId)

    mtr.mtrId = KeysBuilder.getMtrId
    //TODO учесть разные типы счетчиков
    mtr.mtrTypeCd = "E-1F"
    mtr.badgeNbr = potr.nelsch
    mtr.mtrStatusFlg = "A"
    mtr.mfgCd = "CM"
    mtr.modelCd = "CM"
    mtr.serialNbr = potr.nelsch
    mtr.receiveDt = getRecieveDt(potr)

    for (tip <- potr.mt.tip)
      mtr.descrlong = tip


    //рабочее напряжение
    for (volt <- potr.mt.volt) {
      if (voltValid.contains(volt))
        MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "NAPR-PU",charVal = volt, effDt = mtr.receiveDt))
    }

    //класс точности
    for (klast <- filterAccuracyClass(potr.mt.klast))
      MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "KL-TOCHN", charVal = klast, effDt = mtr.receiveDt))

    //межповерочный интервал ПУ
    for (mIn <- potr.mt.mIn)
      MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "PRD-POV", charVal = mIn, effDt = mtr.receiveDt))

    //год выпуска
    for (gw <- potr.filterGw)
      MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "D-VYP-PU", adhocCharVal = gw.toString, effDt = mtr.receiveDt))

    //дата последней поверки
    for (dp <- getCheckDt(potr))
      MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "DT-PP-OV", adhocCharVal = dp, effDt = mtr.receiveDt))

    //вид энергии
    MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "VID-IZ-E", charVal = "A", effDt = mtr.receiveDt))

    //сила тока TOK-PU
    for (amper <- potr.mt.amper)
      MtrBuilder.addChar(mtr,Characteristic(charTypeCd = "TOK-PU", adhocCharVal = amper, effDt = mtr.receiveDt))

    mtr

  }

}
