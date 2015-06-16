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
/*
    val potrList = List(
      Potr(naimp = "naim",
        kelsch = "kelsch",
        volt = "volt",
        address = Address(Some("region"), "city", Some("street"), Some("house"), Some("room"), "postalCode", "inn"),
        nelsch = "nelsch",
        tip = "tip",
        klast = "klast",
        mIn = "mIn",
        gw = "gw",
        dataSh = "01.01.2010",
        amper = "amper",
        rks = 1,
        r2 = 7,
        data = DateBuilder.getDate(2014,11,1),
        data2 = DateBuilder.getDefaultDt))
*/
/*
    LoaderG.load(Plat(
      "1",
      "email",
      Address(Some("regionJ"), "cityJ", Some("streetJ"), Some("houseJ"), Some("roomJ"), "postalCodeJ", Some("innJ")),
      Address(Some("regionF"), "cityF", Some("streetF"), Some("houseF"), Some("roomF"), "postalCodeF", Some("innF")),
      "contractNumber",
      "kpp",
      "nameF",
      "phoneF",
      DateBuilder.getDefaultDt,
      "agnumberL",
      "agNumber",
      potrList)
    )
*/
    assert(true)
  }

  test("load person") {
    val per = LoaderG.findPer(KeysBuilder.getPerId)

    assert((if (per != null) per.perId else null) === KeysBuilder.getPerId)
  }

}
