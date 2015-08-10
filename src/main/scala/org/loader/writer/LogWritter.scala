package org.loader.writer

import grizzled.slf4j.Logging
import org.loader.models.{SpObject, ObjectModel, SubjectModel}
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.reg.RegEntity
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.JavaConversions._
import scala.collection.immutable.HashMap

import scala.collection.JavaConverters._

import java.util.{Map => JMap}

object LogWritter extends Logging {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbc: OutReader = ctx.getBean(classOf[OutReader])

  val sql =
    s"""
       |insert into cm_log_per_gesk_juridical
       |(per_id, acct_id, id_plat, id_rec, sa_id, sp_id, mr_first_id, mr_last_id, type)
       |values
       |(:per_id, :acct_id, :id_plat, :id_rec, :sa_id, :sp_id, :mr_first_id, :mr_last_id, :type)""".stripMargin

  val sqlRegMultiZone =
  s"""
     |insert into cm_log_per_gesk_zone
     |(parent_id, child_id, reg_id, sp_id)
     |values
     |(:parent_id, :child_id, :reg_id, :sp_id)""".stripMargin

  val sqlKeys =
    s"""
       |insert into cm_log_keys_gesk
       |(column_name, key)
       |values
       |(:column_name, :key)""".stripMargin

  def objToMap(subj: SubjectModel, obj: ObjectModel):JMap[String,String] = {
    HashMap(
      "per_id" -> subj.per.perId,
      "acct_id" -> subj.acct.acctId,
      "id_plat" -> subj.plat.idPlat,
      "id_rec" -> obj.potr.idRec,
      "sa_id" -> obj.sa.saId,
      "sp_id" -> obj.sp.spId,
      "mr_first_id" -> obj.mrFirst.mrId,
      "mr_last_id" -> obj.mrLast.mrId,
      "type" -> "OBJECT")
  }

  def spObjToMap(subj: SubjectModel, spObj: SpObject):JMap[String,String] = {
    HashMap(
      "per_id" -> subj.per.perId,
      "acct_id" -> subj.acct.acctId,
      "id_plat" -> subj.plat.idPlat,
      "id_rec" -> spObj.potr.idRec,
      "sa_id" -> "",
      "sp_id" -> spObj.sp.spId,
      "mr_first_id" -> spObj.mrFirst.mrId,
      "mr_last_id" -> spObj.mrLast.mrId,
      "type" -> "SP_OBJECT")
  }

  def subjToListMap(subj: SubjectModel):List[JMap[String,_]] = {
    subj.objects.map((obj) => objToMap(subj = subj, obj = obj)) :::
      subj.spObjects.map((spObj) => spObjToMap(subj = subj, spObj = spObj))
  }

  def regToMap(obj: ObjectModel, reg: (RegEntity, Potr)):JMap[String,String] = {
    HashMap(
      "parent_id" -> obj.potr.idRec,
      "child_id" -> reg._2.idRec,
      "reg_id" -> reg._1.regId,
      "sp_id" -> obj.sp.spId)
  }

  def objToRegListMap(obj:ObjectModel):List[JMap[String,String]] = {
    obj.regList.map((reg) => regToMap(obj = obj, reg = reg))
  }

  def subjToRegListMap(subj:SubjectModel):List[JMap[String,String]] = {
    subj.objects.flatMap((obj) => objToRegListMap(obj = obj))
  }

  def keyToMap(key:(String,String)):JMap[String,String] = {
    HashMap("column_name" -> key._1,"key" -> key._2)
  }

  def log(subjects: List[SubjectModel]) = {
    jdbc.insertBatch(sql,subjects.flatMap(subjToListMap(_)))
    info(s"end write log")
    jdbc.insertBatch(sqlRegMultiZone,subjects.flatMap(subjToRegListMap(_)))
    info(s"end write log zone")
  }

  def logKeys(keys:List[(String,String)]) = {
    jdbc.insertBatch(sqlKeys,keys.map(keyToMap(_)))
    info(s"end write keys")
  }

}
