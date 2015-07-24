package org.loader.builders.general


import org.loader.models.Characteristic
import org.loader.pojo.per._
import scala.language.implicitConversions



object PersonBuilder {

  implicit def buildPerChar(char: Characteristic): PerCharEntity = {

    val perChar = new PerCharEntity()
    perChar.charTypeCd = char.charTypeCd
    perChar.effdt = char.effDt
    perChar.charVal = char.charVal
    perChar.adhocCharVal = char.adhocCharVal

    perChar
  }

  def addPersonChar(person: PerEntity,
                    char: Characteristic) = {

    if (!char.isEmpty)
      person.perCharEntitySet.add(char)
  }

  def addPersonId(person: PerEntity,
                  idTypeCd: String,
                  perIdNbr: String,
                  primSw: String) = {
    if (perIdNbr != null)
      person.perIdEntitySet.add(new PerIdEntity(idTypeCd, perIdNbr, primSw))
  }

//  def addPerPhone(person: PerEntity) = {
//    person.perPhoneEntitySet.add(new PerPhoneEntity())
//  }

  def addPersonName(person: PerEntity,
                    name: String) = {
    if (name != null)
      person.perNameEntitySet.add(new PerNameEntity(name))
  }

  def addPersonKey(person: PerEntity) = {
    person.perKEntitySet.add(new PerKEntity(KeysBuilder.getEnvId))
  }

  def addPersonPhone(person:PerEntity, seqNum: Int, phone:String, phoneType: String) = {
    val perPhone = new PerPhoneEntity()
    perPhone.seqNum = seqNum
    perPhone.phone = phone
    perPhone.phoneTypeCd = phoneType

    person.perPhoneEntitySet.add(perPhone)
  }


}
