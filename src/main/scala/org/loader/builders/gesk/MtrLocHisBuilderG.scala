package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.mtrlochis.MtrLocHisEntity
import org.loader.pojo.spmtrhist.SpMtrHistEntity

object MtrLocHisBuilderG {

  def build(mtr: MtrEntity, spMtrHist: SpMtrHistEntity,locDttm: java.util.Date, stkLocCd: String, locHistTypFlg: String):MtrLocHisEntity = {
    val mtrLocHis = new MtrLocHisEntity(KeysBuilder.getEnvId)

    mtrLocHis.mtrLocHistId = KeysBuilder.getMtrLocHistId
    mtrLocHis.locDttm = locDttm
    mtrLocHis.stkLocCd = "KLIENT"
    mtrLocHis.locHistTypFlg = "SPIN"

    mtrLocHis.spMtrHist = spMtrHist

    mtrLocHis.mtr = mtr
    mtr.mtrLocHisMtrEntitySet.add(mtrLocHis)

    mtrLocHis
  }

  def build(mtr: MtrEntity, spMtrHist: SpMtrHistEntity, readDttm: java.util.Date):Unit = {
    build(mtr = mtr,spMtrHist = spMtrHist, locDttm = DateBuilder.addDay(readDttm,-1),stkLocCd = "KLIENT", locHistTypFlg = "STCK")
    build(mtr = mtr,spMtrHist = spMtrHist, locDttm = readDttm,stkLocCd = " ", locHistTypFlg = "SPIN")
  }

}
