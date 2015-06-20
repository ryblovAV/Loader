package org.loader.db

import grizzled.slf4j.Logging
import org.loader.out.gesk.objects.Plat
import org.loader.out.gesk.reader.GeskReader
import org.scalatest.FunSuite

class DBReadTest extends FunSuite with Logging {

  test("read plat") {

    val platList = GeskReader.readPlat
    assert(platList.isEmpty === false)
    assert(platList.size === 1)

    val plat:Plat = platList.head

    val potrList = plat.potrList
    assert(potrList.isEmpty === false)
    assert(potrList.size === 1)

  }


}
