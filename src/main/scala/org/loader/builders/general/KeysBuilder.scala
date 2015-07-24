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

  def getEnvId = 201302

  def sqlForKey(columnName:String)
  = s"""| select pkg_cm_load_juridical_keys.${columnName}('15') as id
        |   from dual
        |connect by level < 1000""".stripMargin

  def fillMap(sql:String) = {
    val keys = jdbcReader.query(sql)((rs, rowNum) => rs.getString("id"))
    mKeys = mKeys + (sql -> keys)
  }

  def getNextKey(sql:String):String = mKeys(sql) match {
    case h::t => {
      mKeys = mKeys + (sql -> t);
      h
    }
    case Nil  => {
      fillMap(sql)
      getNextKey(sql)
    }
  }

  def getId(sql:String) = getNextKey(sql)

  def getIdOld(sql:String) = jdbcReader.query(sql)((rs, rowNum) => rs.getString("id")).head


  val personSql = sqlForKey(columnName = "per_id")
  def getPerId:String = getId(personSql)

  val acctSql = sqlForKey(columnName = "acct_id")
  def getAcctId = getId(acctSql)

  val premiseSql = sqlForKey(columnName = "prem_id")
  def getPremiseId = getId(spSql)

  val spSql = sqlForKey(columnName = "sp_id")
  def getSpId = getId(spSql)

  val mtrSql = sqlForKey(columnName = "mtr_id")
  def getMtrId = getId(mtrSql)

  val regSql = sqlForKey(columnName = "reg_id")
  def getRegId = getId(regSql)

  val mtrConfigSql = sqlForKey(columnName = "mtr_config_id")
  def getMtrConfigId = getId(mtrConfigSql)

  val mrSql = sqlForKey(columnName = "mr_id")
  def getMrId = getId(mrSql)

  val regReadSql = sqlForKey(columnName = "reg_read_id")
  def getRegReadId = getId(regReadSql)

  val spMtrHistSql = sqlForKey(columnName = "sp_mtr_hist_id")
  def getSpMtrHistId = getId(spMtrHistSql)

  val saSql = sqlForKey(columnName = "sa_id")
  def getSaId = getId(saSql)

  val saSpSql = sqlForKey(columnName = "sa_sp_id")
  def getSaSpId = getId(saSpSql)

  val acctApaySql = sqlForKey(columnName = "acct_apay_id")
  def getAcctApayId = getId(acctApaySql)

  val mtrLocHistSql = sqlForKey(columnName = "mtr_loc_hist_id")
  def getMtrLocHistId = getId(mtrLocHistSql)

  val adjSql = sqlForKey(columnName = "adj_id")
  def getAdjId = getId(adjSql)

  val ftSql = sqlForKey(columnName = "ft_id")
  def getFtId = getId(ftSql)

  val depCtlStSql = sqlForKey(columnName = "dep_ctl_st_id")
  def getExtTransmitId = getId(depCtlStSql)

  val tndrCtlStId = sqlForKey(columnName = "tndr_ctl_st_id")
  def getExtBatchId = getId(tndrCtlStId)

  val payTndrStId = sqlForKey(columnName = "pay_tndr_st_id")
  def getExtReferenceId = getId(payTndrStId)

  

}
