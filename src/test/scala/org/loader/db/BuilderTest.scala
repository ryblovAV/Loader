package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.gesk.{SaBuilderG, OkonhDictionary, AddressBuilderG}
import org.loader.out.gesk.objects.Address
import org.scalatest.FunSuite

class BuilderTest extends FunSuite with Logging {

  test("build address1") {
    val address =
      Address(reg = Some("reg"),
        ul = Some("ul"),
        dom = Some("dom"),
        kv = Some("kv"),
        ind = Some("ind"),
        inn = Some("inn"),
        abv1 = Some("abv1"),
        abv2 = Some("abv2"),
        phone = None)
    assert(AddressBuilderG.buildAddress1(address) === "reg, abv2 ul, д. dom, кв. kv")
    assert(AddressBuilderG.buildAddress1(address.copy(abv2 = None)) === "reg, ul, д. dom, кв. kv")
    assert(AddressBuilderG.buildAddress1(address.copy(ul = None)) === "reg, д. dom, кв. kv")
  }

  test("build address2") {
    val address =
    Address(reg = None,
      ul = Some("Октябрьская"),
      dom = Some("1"),
      kv = Some("148"),
      ind = None,
      inn = None,
      abv1 = Some("д."),
      abv2 = Some("ул."),
      phone = None)
    assert(AddressBuilderG.buildAddress1(address) === "ул. Октябрьская, д. 1, кв. 148")
  }

  test("readOkonh") {
    assert(OkonhDictionary.getOkonh(Some("86")) === Some("999108"))
    assert(OkonhDictionary.getOkonh(None) === None)
    assert(OkonhDictionary.dic.foldLeft(0)((c,t) => c + 1) === 107)
  }

  test("translate napryag") {
    assert(SaBuilderG.translateNaprayg(Some("нн"),None) == Some("0.4"))
    assert(SaBuilderG.translateNaprayg(Some("zzz"),Some("210")) == Some("110"))
    assert(SaBuilderG.translateNaprayg(Some("сн"),Some("210")) == Some("110"))
    assert(SaBuilderG.translateNaprayg(Some("сн"),Some("0")) == Some("6"))
  }

}
