package org.loader.builders.gesk

import org.loader.builders.general.{PremiseBuilder, DateBuilder, KeysBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.{Potr, Address}
import org.loader.pojo.prem.PremEntity

import org.loader.builders.general.BuilderUtl._

object PremiseBuilderG {

  def buildPremise(potr:Potr):PremEntity = {
    val prem = new PremEntity(KeysBuilder.getEnvId)
    prem.premId = KeysBuilder.getPremiseId
    prem.premTypeCd = "PROCHIE"
    prem.cisDivision = GeskConstants.cisDivision
    prem.trendAreaCd = "LIPCOMM"
    prem.address1 = AddressBuilderG.buildAddress1(potr.address)
    prem.address2 = potr.address.dom
    prem.address3 = potr.address.ul
    prem.address4 = potr.address.kv

    //книга
    for (kniga <- potr.kniga)
      PremiseBuilder.addChar(prem,Characteristic(charTypeCd = "BOOK",adhocCharVal = kniga))

    prem
  }

}
