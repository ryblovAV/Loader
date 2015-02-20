package org.loader.db

import java.util.Calendar

import grizzled.slf4j.Logging
import org.junit.runner.RunWith
import org.loader.builders.Utils
import org.loader.builders.gesk.LoaderG
import org.loader.db.utl.DBUtl
import org.loader.out.gesk.objects.{Potr, Address, Plat}
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GeskLoaderTest extends FunSuite with Logging {

  test("load plat") {
    val platList = GeskReader.readPlat("1001")
    assert(!platList.isEmpty)
  }

  ignore("save acct and per") {

    val potrList = List(Potr(naimp = "naim", kelsch = "kelsch", volt = "volt", address = Address("region", "city", "street", "house", "room", "postalCode", "inn")))

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
      Utils.getDefaultDt,
      "agnumberL",
      "agNumber",
      potrList)
    )
    assert(true)
  }


}
