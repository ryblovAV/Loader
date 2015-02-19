package org.loader.builders.gesk

import org.loader.builders.Utils
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.sa.SaEntity

object SaBuilderG {


  def buildSaHistorical(plat: Plat) = {
    val sa = new SaEntity(Utils.getEnvId)

    sa.saId = Utils.getSaId
    sa.saTypeCd = "GCOMHIST"
    sa.startDt = if (plat.dateConclusion != null) plat.dateConclusion else Utils.getDefaultDt
    sa.cisDivision = GeskConstants.cisDivision

    sa
  }
}
