package org.loader.builders

import java.util.Date

import org.loader.out.lesk.objects.Client
import org.loader.pojo.per._

/**
 * Created by a123 on 24/11/14.
 */
object PersonBuilder {

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

  def buildPerChar(charTypeCd: String,
                   charVal: String = " ",
                   adhocCharVal: String = " ",
                   effDt: Date = Utills.getDefaultDt) = {

    val perChar = new PerCharEntity()
    perChar.charTypeCd = charTypeCd
    perChar.effdt = effDt
    perChar.charVal = charVal
    perChar.adhocCharVal = adhocCharVal

    perChar
  }


  def buildPerson(client: Client) = {

    val person = new PerEntity(Utills.getEnvId)

    person.perId = Utills.getPerId
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
      person.perCharEntitySet.add(buildPerChar(charTypeCd = "BUDZHET", charVal = translateBudget(client.budget)))
    //Исполнитель коммунальных услуг (ИКУ)
    if (client.iku == "Да")
      person.perCharEntitySet.add(buildPerChar(charTypeCd = "IKY", adhocCharVal = "Требуется добавить подтверждающий документ"))
    //Дата расторжения
    if (client.dateCancelling != null)
      person.perCharEntitySet.add(buildPerChar(charTypeCd = "DATARAST", adhocCharVal = Utills.dateToStr(client.dateCancelling)))
    //Дата заключения
    if (client.dateConclusion != null)
      person.perCharEntitySet.add(buildPerChar(charTypeCd = "DATAZAKL", adhocCharVal = Utills.dateToStr(client.dateConclusion)))
    //Код отрасли
    if (client.codeDepartment != null)
      person.perCharEntitySet.add(buildPerChar(charTypeCd = "OKONH", charVal = translateCodeDepartment(client.codeDepartment, client.department)))

    //TODO добавить загрузку телефонов (пакет pkg_cm_load_phone_juridical)
    //TODO добавить загрузку руководителей (пакет pkg_cm_load_person_juridical.load_chief)

    person
  }

}
