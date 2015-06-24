package org.loader.builders.gesk

import grizzled.slf4j.Logging
import org.loader.builders.general.BuilderUtl._
import org.loader.builders.general.DateBuilder.dateToStr
import org.loader.builders.general.KeysBuilder
import org.loader.builders.general.PersonBuilder.{addPersonChar, addPersonId, addPersonName}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity

object PersonBuilderG extends Logging{


  def addAcctToPer(per: PerEntity, acct: AcctEntity) = {
    val acctPerEntity = new AcctPerEntity(per,acct)
  }

  def addPersonIdWithCheck(person: PerEntity, perIdNbrOpt:Option[String], idTypeCd:String, primSw: String) = {
    perIdNbrOpt match {
      case Some(perIdNbr) =>  addPersonId(person = person, idTypeCd = idTypeCd, perIdNbr = perIdNbr, primSw = primSw)
      case None =>
    }
  }

  def addPersonIdWithCheck(person: PerEntity, perIdNbrOpt:Option[String], perIdNbrAltOpt:Option[String], idTypeCd:String, primSw: String):Unit = {
    perIdNbrOpt match {
      case Some(perIdNbr) =>  addPersonId(person = person, idTypeCd = idTypeCd, perIdNbr = perIdNbr, primSw = primSw)
      case None => addPersonIdWithCheck(person = person, perIdNbrOpt = perIdNbrAltOpt, idTypeCd = idTypeCd, primSw = primSw)
    }
  }

  def getBudzhet(ko:String):Option[String] = ko match {
    case "10" => Some("B_MES_GOR_POSEL")
    case "9"  => Some("B_OBLAS")
    case "8"  => Some("B_FED")
    case _ => None
  }

  def buildName(abv1:Option[String], naimU:String):String =
    s"${abv1.getOrElse("")} ${naimU}".trim

  def buildPerson(plat: Plat): PerEntity = {

    val person = new PerEntity(KeysBuilder.getEnvId)

    person.perId = KeysBuilder.getPerId
    info(s"person.id = ${person.perId}")

    person.emailid = plat.el_adr
    person.address1 = AddressBuilderG.buildAddress1(plat.addressU)
    person.address2 = plat.addressU.dom
    person.address3 = plat.addressU.ul
    person.address4 = plat.addressU.kv
    person.city = plat.addressU.reg
    person.postal = plat.addressU.ind

    //Идентификаторы
    addPersonIdWithCheck(person = person, idTypeCd = "SHTRIH", perIdNbrOpt = Some(plat.idPlat), primSw = "N")
    addPersonIdWithCheck(person = person, idTypeCd = "NOM_DOG", perIdNbrOpt = plat.kontr, perIdNbrAltOpt = plat.ndGesk, primSw = "Y")
    addPersonIdWithCheck(person = person, idTypeCd = "INN", perIdNbrOpt = plat.addressF.inn, primSw = "N")
    addPersonIdWithCheck(person = person, idTypeCd = "KPP", perIdNbrOpt = plat.kpp, primSw = "N")

    //Наименование
    addPersonName(person = person, name = buildName(plat.addressU.abv1,plat.naimU))

    //Характеристики
    //бюджет
    getBudzhet(plat.ko) match {
      case Some(charVal) => addPersonChar(person = person, Characteristic(charTypeCd = "BUDZHET", charVal = charVal))
      case None =>
    }

    //Исполнитель коммунальных услуг
    plat.kob match {
      case Some(kob) if ((kob == "101") || (kob == "102")) =>
        addPersonChar(
            person = person,
            Characteristic(charTypeCd = "IKY",
            adhocCharVal = "Требуется добавить подтверждающий документ")
        )
      case _ =>
    }

    //Дата заключения
    plat.data match {
      case Some(data) => addPersonChar(person = person, Characteristic(charTypeCd = "DATAZAKL", adhocCharVal = data))
      case None =>
    }


    //OKONH
    OkonhDictionary.getOkonh(plat.kob) match {
      case Some(charVal) => addPersonChar(person = person, Characteristic(charTypeCd = "OKONH", charVal = charVal))
      case None =>
    }

    //TODO Add phone

    person
  }


}
