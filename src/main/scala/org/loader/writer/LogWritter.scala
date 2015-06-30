package org.loader.writer

import org.loader.models.SubjectModel
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.JavaConversions._
import scala.collection.immutable.HashMap

object LogWritter {

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
