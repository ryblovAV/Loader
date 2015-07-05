package org.loader.writer

import java.util

import org.loader.builders.general.KeysBuilder
import org.loader.reader.OutReader
import org.loader.writer.LogWritter._
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.JavaConverters._

import scala.collection.immutable.HashMap

import java.util.{HashMap => JMap, Date}

case class SaSpObject(
    saSpId: String, 
    saId: String, 
    spId: String, 
    startDttm: Date,
    startMrId: String, 
    usageFlg: String,
    usePct: Int)

object SaSpWriter {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbc: OutReader = ctx.getBean(classOf[OutReader])

  val sqlSaSpK =
    s"""
       |insert into rusadm.ci_sa_sp_k
       |(sa_sp_id,env_id)
       |values
       |(:sa_sp_id,:env_id)""".stripMargin

  val sqlSaSp =
  s"""
     |insert into rusadm.ci_sa_sp
     |(sp_id,sa_sp_id,sa_id,start_dttm,start_mr_id,usage_flg,use_pct)
     |values
     |(:sp_id,:sa_sp_id,:sa_id,:start_dttm,:start_mr_id,:usage_flg,:use_pct)""".stripMargin

  def saSpObjectToMap(saObject: SaSpObject):JMap[String,_] = {

    val m = new JMap[String,Any]
    m.put("sa_sp_id",saObject.saSpId)
    m.put("sp_id",saObject.spId)
    m.put("sa_id",saObject.saId)
    m.put("start_dttm",saObject.startDttm)
    m.put("start_mr_id",saObject.startMrId)
    m.put("usage_flg",saObject.usageFlg)

    m
  }

  def saSpObjectToMapKey(saObject: SaSpObject):JMap[String,_] = {
    val m = new JMap[String,Any]
    m.put("sa_sp_id",saObject.saSpId)
    m.put("env_id",KeysBuilder.getEnvId)
    m
  }

  def save(saSpObjects: List[SaSpObject]) = {
    jdbc.insertBatch(sqlSaSp,saSpObjects.map(saSpObjectToMap(_)))
    jdbc.insertBatch(sqlSaSpK,saSpObjects.map(saSpObjectToMapKey(_)))
  }

}
