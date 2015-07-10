package org.loader.builders.gesk

import org.loader.builders.general.BuilderUtl._
import org.loader.builders.general.{KeysBuilder, PremiseBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.{Address, Potr}
import org.loader.pojo.prem.PremEntity

object PremiseBuilderG {

  def defineHouseType(optKv:Option[String]) = optKv match {
    case Some(_) => "2" //квартира
    case _ => "10"      //другое
  }

  def buildPremise(address:Address, optKniga: Option[String] = None, houseType:String):PremEntity = {
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
    prem.houseType = houseType

    //книга
    for (kniga <- optKniga)
      PremiseBuilder.addChar(prem,Characteristic(charTypeCd = "BOOK",adhocCharVal = kniga))

    prem
  }

}
