package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.gesk.LoaderG
import org.loader.out.gesk.reader.GeskReader
import org.loader.writer.LogWritter
import org.scalatest.FunSuite

class DBWriteTest extends FunSuite with Logging{

  //TODO add test
  test("write to database") {
    LoaderG.loadPlat
    assert(true)
  }

}
