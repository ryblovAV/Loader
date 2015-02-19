package org.loader.builders.gesk

import org.loader.builders.Utils
import org.loader.out.gesk.objects.Address
import org.loader.pojo.prem.PremEntity

object PremiseBuilderG {
  def buildPremise(address: Address) = {

    val prem = new PremEntity(Utils.getEnvId)

    prem.premId = Utils.getPremId
    prem.premTypeCd = "PROCHIE"
    prem.cisDivision = GeskConstants.cisDivision
    prem.trendAreaCd = "LIPCOMM"

    prem.address2 = address.house
    prem.address3 = address.street
    prem.address4 = address.room
    prem.postal = address.postalCode
    prem.city = "Липецк"

    prem
  }
}
