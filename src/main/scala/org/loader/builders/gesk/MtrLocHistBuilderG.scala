package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.pojo.mtr.MtrEntity
import org.loader.pojo.mtrlochis.MtrLocHisEntity

object MtrLocHistBuilderG {

  def build(mtr: MtrEntity, locDttm: java.util.Date, stkLocCd: String, locHistTypFlg: String):MtrLocHisEntity = {
    val mtrLocHis = new MtrLocHisEntity(KeysBuilder.getEnvId)

    mtrLocHis.mtr = mtr
    mtrLocHis.locDttm = locDttm
    mtrLocHis.stkLocCd = "KLIENT"
    mtrLocHis.locHistTypFlg = "SPIN"

    mtr.mtrLocHisMtrEntitySet.add(mtrLocHis)

    mtrLocHis
  }

  def build(mtr: MtrEntity, readDttm: java.util.Date):Unit = {
    build(mtr = mtr,locDttm = DateBuilder.addDay(readDttm,-1),stkLocCd = "KLIENT", locHistTypFlg = "STCK")
    build(mtr = mtr,locDttm = readDttm,stkLocCd = " ", locHistTypFlg = "SPIN")
  }

}
