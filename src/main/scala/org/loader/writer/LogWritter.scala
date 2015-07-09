package org.loader.writer

import grizzled.slf4j.Logging
import org.loader.models.{ObjectModel, SubjectModel}
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
       |(per_id, acct_id, id_plat, id_rec, sa_id, sp_id, mr_id)
       |values
       |(:per_id, :acct_id, :id_plat, :id_rec, :sa_id, :sp_id, :mr_id)""".stripMargin

  val sqlRegMultiZone =
  s"""
     |insert into cm_log_per_gesk_zone
     |(parent_id, child_id, reg_id, sp_id)
     |values
     |(:parent_id, :child_id, :reg_id, :sp_id)""".stripMargin


  def objToMap(subj: SubjectModel, obj: ObjectModel):JMap[String,_] = {
    HashMap(
      "per_id" -> subj.per.perId,
      "acct_id" -> subj.acct.acctId,
      "id_plat" -> subj.plat.idPlat,
      "id_rec" -> obj.potr.idRec,
      "sa_id" -> obj.sa.saId,
      "sp_id" -> obj.sp.spId,
      "mr_id" -> obj.mr.mrId)
  }

  def subjToListMap(subj: SubjectModel):List[JMap[String,_]] = {
    subj.objects.map((obj) => objToMap(subj = subj, obj = obj))
  }

  def regToMap(obj: ObjectModel, reg: (RegEntity, Potr)):JMap[String,_] = {
    HashMap(
      "parent_id" -> obj.potr.idRec,
      "child_id" -> reg._2.idRec,
      "reg_id" -> reg._1.regId,
      "sp_id" -> obj.sp.spId)
  }

  def objToRegListMap(obj:ObjectModel):List[JMap[String,_]] = {
    obj.regList.map((reg) => regToMap(obj = obj, reg = reg))
  }

  def subjToRegListMap(subj:SubjectModel):List[JMap[String,_]] = {
    subj.objects.flatMap((obj) => objToRegListMap(obj = obj))
  }

  def log(subjects: List[SubjectModel]) = {
    jdbc.insertBatch(sql,subjects.flatMap(subjToListMap(_)))
    info(s"end $sql")
    jdbc.insertBatch(sqlRegMultiZone,subjects.flatMap(subjToRegListMap(_)))
    info(s"end $sqlRegMultiZone")
  }

  def log(subj: SubjectModel) = {

    for (obj <- subj.objects) {
      jdbc.insert(sql, HashMap(
        "per_id" -> subj.per.perId,
        "acct_id" -> subj.acct.acctId,
        "id_plat" -> subj.plat.idPlat,
        "id_rec" -> obj.potr.idRec,
        "sa_id" -> obj.sa.saId,
        "sp_id" -> obj.sp.spId,
        "mr_id" -> obj.mr.mrId))

      for (reg <- obj.regList) {
        jdbc.insert(sqlRegMultiZone, HashMap(
          "parent_id" -> obj.potr.idRec,
          "child_id" -> reg._2.idRec,
          "reg_id" -> reg._1.regId,
          "sp_id" -> obj.sp.spId))
      }
    }
  }
}
