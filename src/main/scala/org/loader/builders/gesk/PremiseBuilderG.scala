package org.loader.builders.gesk

import grizzled.slf4j.Logging
import org.loader.builders.general.BuilderUtl._
import org.loader.builders.general.{KeysBuilder, PremiseBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.{Address, Potr}
import org.loader.pojo.prem.PremEntity

object PremiseBuilderG extends Logging {

  def defineHouseType(optKv:Option[String]) = optKv match {
    case Some(_) => "2" //квартира
    case _ => "10"      //другое
  }

  def buildPremise(address:Address, optKniga: Option[String] = None):PremEntity = {
    val prem = new PremEntity(KeysBuilder.getEnvId)
    prem.premId = KeysBuilder.getPremiseId
    prem.premTypeCd = "PROCHIE"
    prem.cisDivision = GeskConstants.cisDivision
    prem.trendAreaCd = "LIP_COM"
    prem.address1 = AddressBuilderG.buildAddress1(address)
    prem.address2 = address.dom
    prem.address3 = address.ul
    prem.address4 = address.kv
    prem.country = "RUS"
    prem.timeZoneCd = "MSK"
    prem.lsSlFlg = "N"
    prem.state = "20"

    prem.postal = address.ind
    prem.city = address.reg

    //книга
    for (kniga <- optKniga)
      PremiseBuilder.addChar(prem,Characteristic(charTypeCd = "BOOK",adhocCharVal = kniga))

    prem
  }

}
