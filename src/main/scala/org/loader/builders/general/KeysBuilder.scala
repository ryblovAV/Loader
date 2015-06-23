package org.loader.builders.general

import grizzled.slf4j.Logging
import org.loader.reader.JdbcTemplatesUtl._
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

object KeysBuilder extends Logging {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  def getEnvId = 201302

  def sqlForKey(columnName:String, tableName: String, schemeName: String = "stgadm")
    = s"select pkg_cm_load_juridical_keys.${columnName}('15') as id from dual"
//    = s"select max(to_number($columnName))+1 as id from $schemeName.$tableName"


  def getId(sql:String) = jdbcReader.query(sql)((rs, rowNum) => rs.getString("id")).head

  val personSql = sqlForKey(columnName = "per_id", tableName = "ci_per_k", schemeName = "stgadm")
  def getPerId:String = getId(personSql)

  val acctSql = sqlForKey(columnName = "acct_id", tableName = "ci_acct_k", schemeName = "stgadm")
  def getAcctId = getId(acctSql)

  val premiseSql = sqlForKey(columnName = "prem_id", tableName = "ci_prem_k", schemeName = "stgadm")
  def getPremiseId = getId(spSql)

  val spSql = sqlForKey(columnName = "sp_id", tableName = "ci_sp_k", schemeName = "stgadm")
  def getSpId = getId(spSql)

  val mtrSql = sqlForKey(columnName = "mtr_id", tableName = "ci_mtr_k", schemeName = "stgadm")
  def getMtrId = getId(mtrSql)

  val regSql = sqlForKey(columnName = "reg_id", tableName = "ci_reg_k", schemeName = "stgadm")
  def getRegId = getId(regSql)

  val mtrConfigSql = sqlForKey(columnName = "mtr_config_id", tableName = "ci_mtr_config_k", schemeName = "stgadm")
  def getMtrConfigId = getId(mtrConfigSql)

  val mrSql = sqlForKey(columnName = "mr_id", tableName = "ci_mr_k", schemeName = "stgadm")
  def getMrId = getId(mrSql)

  val regReadSql = sqlForKey(columnName = "reg_read_id", tableName = "ci_reg_read_k", schemeName = "stgadm")
  def getRegReadId = getId(regReadSql)

  val spMtrHistSql = sqlForKey(columnName = "sp_mtr_hist_id", tableName = "ci_sp_mtr_hist_k", schemeName = "stgadm")
  def getSpMtrHistId = getId(spMtrHistSql)

  val saSql = sqlForKey(columnName = "sa_id", tableName = "ci_sa_k", schemeName = "stgadm")
  def getSaId = getId(saSql)

  val saSpSql = sqlForKey(columnName = "sa_sp_id", tableName = "ci_sa_sp_k", schemeName = "stgadm")
  def getSaSpId = getId(saSpSql)

  val acctApaySql = sqlForKey(columnName = "acct_apay_id", tableName = "ci_acct_apay_k", schemeName = "stgadm")
  def getAcctApayId = getId(acctApaySql)

  val mtrLocHistSql = sqlForKey(columnName = "mtr_loc_hist_id", tableName = "mtr_loc_hist_k", schemeName = "stgadm")
  def getMtrLocHistId = getId(mtrLocHistSql)

  val adjSql = sqlForKey(columnName = "adj_id", tableName = "adj_k", schemeName = "stgadm")
  def getAdjId = getId(adjSql)

  val ftSql = sqlForKey(columnName = "ft_id", tableName = "ft_k", schemeName = "stgadm")
  def getFtId = getId(ftSql)

  val depCtlStSql = sqlForKey(columnName = "dep_ctl_st_id", tableName = "ci_dep_ctl_st", schemeName = "stgadm")
  def getExtTransmitId = getId(depCtlStSql)

  val tndrCtlStId = sqlForKey(columnName = "tndr_ctl_st_id", tableName = "ci_tndr_ctl_st", schemeName = "stgadm")
  def getExtBatchId = getId(tndrCtlStId)

  val payTndrStId = sqlForKey(columnName = "pay_tndr_st_id", tableName = "ci_pay_tndr_st", schemeName = "stgadm")
  def getExtReferenceId = getId(payTndrStId)

  

}
