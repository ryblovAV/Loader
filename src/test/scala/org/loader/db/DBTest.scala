package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.gesk.LoaderG
import org.loader.out.gesk.objects.Potr
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite

class DBTest extends FunSuite with Logging{

  ignore("build person") {
    val platList = GeskReader.readPlat("1001").foreach(LoaderG.load(_))
    assert(true)
  }

  test("read potr") {
    for (plat <- GeskReader.readPotrForPlat("1008"))
      info(plat)
    assert(true)
  }


}
