package org.loader.builders.gesk

import org.loader.out.gesk.objects.Address

object AddressBuilderG {

  def addPair(a: Option[String], b: Option[String], s:String) = b match {
    case Some(vb) => a match {
      case Some(va) => s"$va $vb$s "
      case None => s"$vb$s "
    }
    case None => ""
  }

  def getRegStr(regOpt: Option[String]) = regOpt match {
    case Some(reg) => s"$reg, "
    case _ => ""
  }

  def buildAddress1(a: Address) = {
    s"${getRegStr(a.reg)}${addPair(a.abv2,a.ul,",")}${addPair(Some("д."),a.dom,",")}${addPair(Some("кв."),a.kv,"")}".trim
  }


}
