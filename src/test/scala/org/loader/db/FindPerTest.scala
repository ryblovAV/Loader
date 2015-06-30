package org.loader.db

import grizzled.slf4j.Logging
import org.loader.builders.gesk.LoaderG
import org.loader.pojo.per.PerEntity
import org.scalatest.FunSuite

import scala.collection.JavaConversions._

class FindPerTest extends FunSuite with Logging{

  test("find per") {
    val perId = "5150003666"

    val per = LoaderG.findPer(perId)
    assert(per.perId === perId)

    val apList = per.acctPerEntities
    assert(apList.size === 1)

    val acct = apList.head.acct

    val saList = acct.saEntitySet
    assert(saList.size === 1)

  }




  def print(per: PerEntity) {
    info("----------- perChar")
    for (p <- per.perCharEntitySet) {
      info(s"${p.charTypeCd} = ${p.charVal} ${p.adhocCharVal}")
    }
    info("----------- perName")
    for (p <- per.perNameEntitySet) {
      info(s"${p.entityName} ${p.nameTypeFlg}")
    }
    info("----------- perId")
    for (p <- per.perIdEntitySet) {
      info(s"${p.idTypeCd} = ${p.perIdNbr}")
    }
    info("----------- acct")
    for (ap <- per.acctPerEntities) {
      val acct = ap.acct
      info(s"acctId = ${acct.acctId} cisDivision = ${acct.cisDivision}")

      info("----------- acct_char")
      for (a <- acct.acctCharEntitySet) {
        info(s"${a.charTypeCd}  ${a.adhocCharVal} ${a.charVal}")
      }

      info("---------- sa")
      for (sa <- acct.saEntitySet) {
        info(s"sa_id ${sa.saId} sa_type_cd = ${sa.saTypeCd}")

        for (sasp <- sa.saSpEntitySet) {
          info("------- sasp -----------")
          info(s"sa_sp_id = ${sasp.saSpId} usageFlg = ${sasp.usageFlg}  startDttm = ${sasp.startDttm} startMrId = ????")

          info("----- sp ------")
          val sp = sasp.sp
          info(s"sp_id = ${sp.spId} installDt = ${sp.installDt}")

          for (spMtrHist <- sp.spMtrHistEntity) {

            info("------- spMtrHist -------")
            info(s"spMtrHistId = ${spMtrHist.spMtrHistId}")


            info("------- mtrConfig -------")
            val mtrCfg = spMtrHist.mtrCfg
            info(s"mtrCfgId = ${mtrCfg.mtrConfigId}")

            info("------- mtr -------")
            val mtr = mtrCfg.mtr
            info(s"mtrId = ${mtrCfg.mtr.mtrId}")

            for (mtrLocHis <- mtr.mtrLocHisMtrEntitySet) {
              info("------- mtrLocHis -------")
              info(s"mtrLocHistId = ${mtrLocHis.mtrLocHistId}")
            }

            info("------- reg -------")
            for (reg <- spMtrHist.mtrCfg.mtr.regMtrEntitySet) {
              info(s"regId = ${reg.regId}  touCd = ${reg.touCd}")
            }

            info("-------- mr -------")
            for (mr <- mtrCfg.mrMtrConfigEntitySet) {
              info(s"mrId = ${mr.mrId} readDttm = ${mr.readDttm}")
              info("------- reg_read -------------")
              for (regRead <- mr.regReadMrEntitySet) {
                info(s"reg_read_id =  ${regRead.regReadId}  regReading = ${regRead.regReading}")
              }
            }




          }

        }


      }
      info("========== END ==============")

    }





  }

}
