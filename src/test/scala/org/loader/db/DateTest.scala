package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.general.DateBuilder
import org.loader.builders.gesk.LoaderG
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite

class DateTest extends FunSuite with Logging {

  val dt = DateBuilder.getDate(2015,2,1)
  
  test(""){
    assert(DateBuilder.lastDay(dt) === DateBuilder.getDate(2015,2,28))
  }

  test(""){
    assert(DateBuilder.addDay(dt) === DateBuilder.getDate(2015,1,31))
  }

}
