package org.loader.builders.lesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.builders.general.PersonBuilderUtl.buildPerChar
import org.loader.models.Characteristic
import org.loader.out.lesk.objects.Client
import org.loader.pojo.per._

object PersonBuilderL {

  def translateBudget(budget: String) = {
    budget match {
      case "Областной" => "B_OBLAS"
      case "Городской" => "B_MES_GOR_POSEL"
      case "Районный" => "B_MES_MUN_RAION"
      case "Федеральный" => "B_FED"
      case _ => throw new Exception(s"Unknown budget $budget ")
    }
  }

  def translateCodeDepartment(codeDepartment: String, department: String) = {
    if (department == "ПРОЧИЕ ОТРАСЛИ")
      codeDepartment.trim + "*"
    else
      codeDepartment
  }


  def buildPerson(client: Client) = {

    val person = new PerEntity(KeysBuilder.getEnvId)

    person.perId = KeysBuilder.getPerId
    person.emailid = client.email
    person.address1 = client.address
    person.address2 = client.houseU
    person.address3 = client.streetU
    person.city = client.townU
    person.postal = client.postcodeU



    // Идентификаторы
    // ИД клиента
    person.perIdEntitySet.add(new PerIdEntity("ID_CLIEN", client.id, "N"))
    // Номер договора
    person.perIdEntitySet.add(new PerIdEntity("NOM_DOG", client.contract, "Y"))
    // ИНН
    person.perIdEntitySet.add(new PerIdEntity("INN", client.inn, "N"))
    // КПП
    person.perIdEntitySet.add(new PerIdEntity("KPP", client.kpp, "N"))

    //Наименования
    person.perNameEntitySet.add(new PerNameEntity(client.name))

    //Характеристики
    //Бюджет
    if (client.budget != null)
      person.perCharEntitySet.add(Characteristic(charTypeCd = "BUDZHET", charVal = translateBudget(client.budget)))
    //Исполнитель коммунальных услуг (ИКУ)
    if (client.iku == "Да")
      person.perCharEntitySet.add(Characteristic(charTypeCd = "IKY", adhocCharVal = "Требуется добавить подтверждающий документ"))
    //Дата расторжения
    if (client.dateCancelling != null)
      person.perCharEntitySet.add(Characteristic(charTypeCd = "DATARAST", adhocCharVal = DateBuilder.dateToStr(client.dateCancelling)))
    //Дата заключения
    if (client.dateConclusion != null)
      person.perCharEntitySet.add(Characteristic(charTypeCd = "DATAZAKL", adhocCharVal = DateBuilder.dateToStr(client.dateConclusion)))
    //Код отрасли
    if (client.codeDepartment != null)
      person.perCharEntitySet.add(Characteristic(charTypeCd = "OKONH", charVal = translateCodeDepartment(client.codeDepartment, client.department)))

    //TODO добавить загрузку телефонов (пакет pkg_cm_load_phone_juridical)
    //TODO добавить загрузку руководителей (пакет pkg_cm_load_person_juridical.load_chief)

    person
  }

}
