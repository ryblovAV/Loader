package org.loader.db

import grizzled.slf4j.Logging
import org.loader.writer.LogWritter
import org.scalatest.FunSuite

class LoggerTest extends FunSuite with Logging {
  test("write to log") {
    LogWritter.log("test~1","test~12345","test~12345")
  }
}
