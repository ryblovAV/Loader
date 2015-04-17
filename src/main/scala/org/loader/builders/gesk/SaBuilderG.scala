package org.loader.builders.gesk

import org.loader.out.gesk.objects.{Potr, Plat}
import org.loader.pojo.prem.PremEntity
import org.loader.pojo.sa.SaEntity

import org.loader.builders.general.{DateBuilder, KeysBuilder, Constants}
object SaBuilderG {

  def buildSaHistorical(plat: Plat, charPrem: PremEntity) = {
    val sa = new SaEntity(KeysBuilder.getEnvId)

    sa.saId = KeysBuilder.getSaIdH
    sa.saTypeCd = GeskConstants.saHistoryTypeCd
    sa.startDt = if (plat.dateConclusion != null) plat.dateConclusion else DateBuilder.getDefaultDt
    sa.endDt = LoaderG.stopHistoricalDt
    sa.cisDivision = GeskConstants.cisDivision
    sa.saStatusFlg = Constants.saCloseStatus

    sa.charPrem = charPrem
    
    sa
  }

  def buildSaForSp(plat: Plat, potr: Potr, charPrem: PremEntity) = {

    val sa = new SaEntity(KeysBuilder.getEnvId)

    sa.saId = KeysBuilder.getSaIdA
    sa.saTypeCd = GeskConstants.saCommercialTypeCd
    sa.startDt = if (LoaderG.startActiveDt.before(plat.dateConclusion)) plat.dateConclusion else  LoaderG.startActiveDt
    sa.cisDivision = GeskConstants.cisDivision
    sa.saStatusFlg = Constants.saOpenStatus

    sa.charPrem = charPrem
    
    sa
  }

}
