package org.loader.db


import grizzled.slf4j.Logging
import org.loader.builders.general.KeysBuilder
import org.loader.builders.gesk.{OkonhDictionary, AddressBuilderG}
import org.scalatest.FunSuite

class KeyTest extends FunSuite with Logging {

  test("person") {
    assert(KeysBuilder.getPerId !== "0")
  }

}
