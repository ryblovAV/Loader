package org.loader.out.lesk.reader

import java.util.Calendar

import org.loader.out.lesk.objects.Client

/**
 * Created by a123 on 24/11/14.
 */
object TestReader {

  def getClient = {

    Client(id = "1", codeBase = "1", name = "name", email = "a@yandex.ru", address = "address1",
      houseU = "houseU",
      streetU = "StreetU",
      postcodeU = "postcodeU",
      townU = "townU",
      contract = "contract",
      inn = "inn",
      iku = "iku",
      kpp = "kpp",
      budget = "Областной",
      phone = "1111111",
      phone_d = "11111",
      codeDepartment = "codeDepartment",
      department = "department",
      dateCancelling = Calendar.getInstance().getTime,
      dateConclusion = Calendar.getInstance().getTime,
      currentAccount = "1",
      codeBank = "001"
    )

  }

}
