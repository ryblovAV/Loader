package org.loader.builders.general

import grizzled.slf4j.Logging

case class PhoneWithType(phoneType:String,phone:String)

object PhoneParser extends Logging {

  val PatternDigitAndOther = "(^[\\d]+)(.+)".r

  val PatternSixDigit = "([\\d]{2})([\\d]{2})([\\d]{2})".r
  val PatternElevenDigit = "(8{1})([\\d]{3})([\\d]{3})([\\d]{4})".r
  val PatternTenDigit = "([\\d]{3})([\\d]{3})([\\d]{4})".r

  val CELL_PHONE = "CELL_PHONE"
  val WORK_PHONE = "WORK_PHONE"

  def extractFirstDigitPath(str: String):String = str match {
    case PatternDigitAndOther(d, s) => d
    case _ => "?"
  }

  def correctString(str: String) = str.split(" ").head.replace("-", "").trim

  def parseOne(phone: String): Option[PhoneWithType] = correctString(phone) match {
      case PatternSixDigit(d1, d2, d3) => Some(PhoneWithType(WORK_PHONE, s"(4742)$d1-$d2-$d3"))
      case PatternTenDigit(d1, d2, d3) => Some(PhoneWithType(CELL_PHONE, s"($d1)$d2-$d3"))
      case PatternElevenDigit(d0, d1, d2, d3) => Some(PhoneWithType(CELL_PHONE, s"($d1)$d2-$d3"))
      case _ => None
  }

  def parse(phone:String):List[Option[PhoneWithType]] = {
    phone.split(' ').map((str) => parseOne(str)).toList
  }

}
