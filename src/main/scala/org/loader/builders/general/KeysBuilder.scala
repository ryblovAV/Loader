package org.loader.builders.general

import grizzled.slf4j.Logging
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import org.loader.reader.JDBCExtractorSafe._
import org.loader.reader.JdbcTemplatesUtl._

object KeysBuilder extends Logging {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  def getEnvId = 201302

  def sqlForKey(columnName:String, tableName: String, schemeName: String = "stgadm")
    = s"select max(to_number($columnName))+1 as id from $schemeName.$tableName"


  def getId(sql:String) = jdbcReader.query(sql)((rs, rowNum) => rs.getString("id")).head

  val personSql = sqlForKey(columnName = "per_id", tableName = "ci_per_k", schemeName = "stgadm")
  def getPerId:String = getId(personSql)

  val acctSql = sqlForKey(columnName = "acct_id", tableName = "ci_acct_k", schemeName = "stgadm")
  def getAcctId = getId(acctSql)

  val premiseSql = sqlForKey(columnName = "prem_id", tableName = "ci_prem_k", schemeName = "stgadm")
  def getPremiseId = getId(spSql)

  val spSql = sqlForKey(columnName = "sp_id", tableName = "ci_sp_k", schemeName = "stgadm")
  def getSpId = getId(spSql)

  val mtrSql = sqlForKey(columnName = "mtr_id", tableName = "ci_sp_k", schemeName = "stgadm")
  def getMtrId = getId(mtrSql)

  val regSql = sqlForKey(columnName = "reg_id", tableName = "ci_reg", schemeName = "stgadm")
  def getRegId = getId(regSql)

  val mtrConfigSql = sqlForKey(columnName = "mtr_config_id", tableName = "ci_mtr_config", schemeName = "stgadm")
  def getMtrConfigId = getId(mtrConfigSql)

  val mrSql = sqlForKey(columnName = "mr_id", tableName = "ci_mr", schemeName = "stgadm")
  def getMrId = getId(mrSql)

  val regReadSql = sqlForKey(columnName = "reg_read_id", tableName = "ci_reg_read", schemeName = "stgadm")
  def getRegReadId = getId(regReadSql)

  val spMtrHistId = sqlForKey(columnName = "sp_mtr_hist_id", tableName = "ci_sp_mtr_hist", schemeName = "stgadm")
  def getSpMtrHistId = getId(spMtrHistId)

  def getAcctApayId = "9123456789"

  def getSaIdH = "sa_hist"
  def getSaIdA = "sa_comm"


}
