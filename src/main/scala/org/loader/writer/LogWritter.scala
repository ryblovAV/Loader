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
       |(per_id, acct_id, id_plat, id_rec, sa_id, sp_id, mr_first_id, mr_last_id, type, sp_type_cd)
       |values
       |(:per_id, :acct_id, :id_plat, :id_rec, :sa_id, :sp_id, :mr_first_id, :mr_last_id, :type, :sp_type_cd)""".
      stripMargin

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

  def spObjToMap(subj: SubjectModel, obj: ObjectModel, spObj: SpObject):JMap[String,String] = {
    HashMap(
      "per_id" -> subj.per.perId,
      "acct_id" -> subj.acct.acctId,
      "id_plat" -> subj.plat.idPlat,
      "id_rec" -> obj.potr.idRec,
      "sa_id" -> obj.sa.saId,
      "sp_id" -> spObj.sp.spId,
      "mr_first_id" -> spObj.mrFirst.map(_.mrId).getOrElse(" "),
      "mr_last_id" -> spObj.mrLast.map(_.mrId).getOrElse(" "),
      "type" -> "OBJECT",
      "sp_type_cd" -> spObj.spTypeCd)
  }

  def spObjToMap(subj: SubjectModel, spObj: SpObject):JMap[String,String] = {
    HashMap(
      "per_id" -> subj.per.perId,
      "acct_id" -> subj.acct.acctId,
      "id_plat" -> subj.plat.idPlat,
      "id_rec" -> spObj.potr.idRec,
      "sa_id" -> "",
      "sp_id" -> spObj.sp.spId,
      "mr_first_id" -> spObj.mrFirst.map(_.mrId).getOrElse(" "),
      "mr_last_id" -> spObj.mrLast.map(_.mrId).getOrElse(" "),
      "type" -> "SP_OBJECT",
      "sp_type_cd" -> spObj.spTypeCd)
  }

  def objToListMap(subj: SubjectModel, obj: ObjectModel):List[JMap[String,_]] = {
    obj.spObjects.map((spObj) => spObjToMap(subj = subj, obj = obj, spObj = spObj))
  }

  def subjToListMap(subj: SubjectModel):List[JMap[String,_]] = {
    subj.objects.flatMap(
      (obj) => objToListMap(subj = subj, obj = obj)) :::
    subj.spObjects.map(
      (spObj) => spObjToMap(subj = subj, spObj = spObj))
  }

  def regToMap(obj: ObjectModel, spObj: SpObject, reg: (RegEntity, Potr)):JMap[String,String] = reg match {
    case (reg,potr) => HashMap(
      "parent_id" -> obj.potr.idRec,
      "child_id" -> potr.idRec,
      "reg_id" -> reg.regId,
      "sp_id" -> spObj.sp.spId)
  }

  def spObjToRegList(obj: ObjectModel, spObj: SpObject):List[JMap[String,String]] = {
    spObj.regList.map((reg) => regToMap(obj = obj, spObj = spObj, reg = reg))
  }

  def objToRegListMap(obj:ObjectModel):List[JMap[String,String]] = {
    obj.spObjects.flatMap((spObj) => spObjToRegList(obj = obj,spObj = spObj))
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
