package org.loader.builders.gesk

import org.loader.builders.Utills
import org.loader.pojo.sa.SaEntity

object SaBuilderG {


  def buildSaHistorical = {
    val sa = new SaEntity(Utills.getEnvId)

    sa.saTypeCd = "GCOMHIST"
    sa.cisDivision = GeskConstants.cisDivision

    sa
  }
}
