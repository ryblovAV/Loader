package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.general.{PhoneWithType, PhoneParser}
import org.scalatest.FunSuite

class PhoneParserTest extends FunSuite with Logging {

  test("parse 6 digit string") {
    val phoneStr = "270877 гл.бух."
    assert(PhoneParser.parse(phoneStr) === List(Some(PhoneWithType("WORK_PHONE","(4742)27-08-77")),None))
  }

  test("parse 6 digit string with '-'") {
    val phoneStr = "27-08-77 гл.бух."
    assert(PhoneParser.parse(phoneStr) === List(Some(PhoneWithType("WORK_PHONE","(4742)27-08-77")),None))

  }

  test("parse 10 digit") {
    val phoneStr = "9066834026"
    assert(PhoneParser.parse(phoneStr) === List(Some(PhoneWithType("CELL_PHONE","(906)683-4026"))))
  }

  test("parse many phone") {
    val phoneStr = "9066834026 as 27-08-77 гл.бух."
    assert(PhoneParser.parse(phoneStr) === List(Some(PhoneWithType("CELL_PHONE","(906)683-4026")),None,Some(PhoneWithType("WORK_PHONE","(4742)27-08-77")),None))
  }


  test("extract digit") {
    val phoneStr = "9066834026asdaskdkjashdka"
    assert(PhoneParser.extractFirstDigitPath(phoneStr) === "9066834026")
  }

  test("extract digit2") {
    val phoneStr = "270877 гл.бух."
    assert(PhoneParser.extractFirstDigitPath(phoneStr) === "270877")
  }

}
