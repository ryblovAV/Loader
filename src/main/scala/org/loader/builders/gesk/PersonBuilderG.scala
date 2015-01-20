package org.loader.builders.gesk

import org.loader.builders.Utills
import org.loader.builders.Utills.dateToStr
import org.loader.builders.general.PersonBuilderUtl.{addPersonChar, addPersonId, addPersonName}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.per.PerEntity

object PersonBuilderG {

  def buildPerson(plat: Plat): PerEntity = {

    val person = new PerEntity(Utills.getEnvId)

    person.perId = Utills.getPerId

    person.emailid = plat.email
    person.address1 = plat.addressJ.region
    person.address2 = plat.addressJ.house
    person.address3 = plat.addressJ.street
    person.address4 = plat.addressJ.room
    person.postal = plat.addressJ.postalCode

    //Идентификаторы
    addPersonId(person = person, idTypeCd = "SHTRIH", perIdNbr = plat.id, primSw = "Y")
    addPersonId(person = person, idTypeCd = "INN", perIdNbr = plat.inn, primSw = "N")
    addPersonId(person = person, idTypeCd = "KPP", perIdNbr = plat.kpp, primSw = "N")

    //Наименование
    addPersonName(person = person, name = plat.nameF)

    //Характеристики
    addPersonChar(person = person, Characteristic(charTypeCd = "DATARAST", adhocCharVal = plat.dtCancellation))
    addPersonChar(person = person, Characteristic(charTypeCd = "DATAZAKL", adhocCharVal = plat.dtConclusion))

    person
  }


}
