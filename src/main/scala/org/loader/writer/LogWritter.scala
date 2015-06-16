package org.loader.writer

import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.JavaConversions._


import scala.collection.immutable.HashMap

object LogWritter {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbc: OutReader = ctx.getBean(classOf[OutReader])

  val sql = "insert into cm_log_per_gesk_juridical (per_id, acct_id, id_plat) values (:per_id, :acct_id, :id_plat)"

  def log(idPlat:String, perId: String, acctId: String) = {
    jdbc.insert(sql,HashMap("id_plat" -> idPlat,"per_id" -> perId,"acct_id" -> acctId))
  }
}
