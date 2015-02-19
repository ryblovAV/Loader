package org.loader.db

import java.util.Calendar

import grizzled.slf4j.Logging
import org.junit.runner.RunWith
import org.loader.builders.Utils
import org.loader.builders.gesk.LoaderG
import org.loader.db.utl.DBUtl
import org.loader.out.gesk.objects.{Address, Plat}
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GeskLoader extends FunSuite with Logging {

  test("load plat") {
    val platList = GeskReader.readPlat
    assert(!platList.isEmpty)
  }

  test("save acct and per") {
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
      "agNumber")
    )
    assert(true)
  }


}
