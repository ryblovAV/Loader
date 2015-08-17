package org.loader.builders.general

import grizzled.slf4j.Logging
import org.loader.reader.JdbcTemplatesUtl._
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext
import scala.language.reflectiveCalls

object KeysBuilder extends Logging {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  var mKeys = Map.empty[String,List[String]].withDefaultValue(List.empty[String])

  var createdKeys = List.empty[(String,String)]

  def getEnvId = 201302

  def sqlForKey(columnName:String)
  = s"""| select pkg_cm_load_juridical_keys.${columnName}('15') as id
        |   from dual
        |connect by level < 1000""".stripMargin

  def fillMap(keyColumn:String) = {
    val keys = jdbcReader.query(sqlForKey(columnName = keyColumn))((rs, rowNum) => rs.getString("id"))
    mKeys = mKeys + (keyColumn -> keys)
  }

  def getNextKey(keyColumn:String):String = mKeys(keyColumn) match {
    case h::t => {
      mKeys = mKeys + (keyColumn -> t)
      h
    }
    case Nil  => {
      fillMap(keyColumn)
      getNextKey(keyColumn)
    }
  }

  def getId(keyColumn:String) = {
    val key = getNextKey(keyColumn)
    createdKeys = (keyColumn,key)::createdKeys
    key
  }

  def getIdOld(sql:String) = jdbcReader.query(sql)((rs, rowNum) => rs.getString("id")).head

  def getPerId:String = getId("per_id")

  def getAcctId = getId("acct_id")

  def getPremiseId = getId("prem_id")

  def getSpId = getId("sp_id")

  def getMtrId = getId("mtr_id")

  def getRegId = getId("reg_id")

  def getMtrConfigId = getId("mtr_config_id")

  def getMrId = getId("mr_id")

  def getRegReadId = getId("reg_read_id")

  def getSpMtrHistId = getId("sp_mtr_hist_id")

  def getSaId = getId("sa_id")

  def getSaSpId = getId("sa_sp_id")

  def getAcctApayId = getId("acct_apay_id")

  def getMtrLocHistId = getId("mtr_loc_hist_id")

  def getAdjId = getId("adj_id")

  def getFtId = getId("ft_id")

  def getExtTransmitId = getId("dep_ctl_st_id")

  def getExtBatchId = getId("tndr_ctl_st_id")

  def getExtReferenceId = getId("pay_tndr_st_id")

  def getBillableChgId = getId("bill_chg_id")
  

}
