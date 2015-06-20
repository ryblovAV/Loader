package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.gesk.LoaderG
import org.loader.out.gesk.objects.Potr
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite

class DBWriteTest extends FunSuite with Logging{

  test("build person") {
    val platList = GeskReader.readPlat.foreach(LoaderG.load(_))
    assert(true)
  }

}
