package org.loader.db

import grizzled.slf4j.Logging
import org.junit.runner.RunWith
import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.builders.gesk.LoaderG
import org.loader.out.gesk.objects.{Address, Plat, Potr}
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GeskLoaderTest extends FunSuite with Logging {

  ignore("load plat") {
    val platList = GeskReader.readPlat("1001")
    assert(!platList.isEmpty)
  }

  test("remove") {
    LoaderG.removePer(KeysBuilder.getPerId)
  }

  test("save acct and per") {

    val potrList = List(
      Potr(naimp = "naim", kelsch = "kelsch", volt = "volt", address = Address("region", "city", "street", "house", "room", "postalCode", "inn")))

    LoaderG.load(Plat(
      "1",
      "email",
      Address("regionJ", "cityJ", "streetJ", "houseJ", "roomJ", "postalCodeJ", "innJ"),
      Address("regionF", "cityF", "streetF", "houseF", "roomF", "postalCodeF", "innF"),
      "contractNumber",
      "inn",
      "kpp",
      "nameF",
      "phoneF",
      DateBuilder.getDefaultDt,
      "agnumberL",
      "agNumber",
      potrList)
    )
    assert(true)
  }

  test("load person") {
    val per = LoaderG.findPer(KeysBuilder.getPerId)

    assert((if (per != null) per.perId else null) === KeysBuilder.getPerId)
  }

}
