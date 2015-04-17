package org.loader.out.gesk.reader

import org.loader.out.gesk.objects.{Address, Plat, Potr}
import org.loader.reader.JDBCExtractorSafe._
import org.loader.reader.JdbcTemplatesUtl._
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.JavaConversions._
import scala.collection.immutable.HashMap

object GeskReader {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  final val city = "Липецк"

  final val sqlPlat = "select * from v_gesk_plat where id_plat = :id_plat"

  final val sqlPotr = "select * from v_gesk_potr where id_plat = :id_plat"

  def readPlat(idPlat: String): List[Plat] = {

    val potrList: List[Potr] = readPotrForPlat(idPlat)

    jdbcReader.queryWithParameters(sqlPlat, HashMap("id_plat" -> idPlat)) {
      (rs, rowNum) =>
        Plat(
          id = (rs, "id_plat"),
          email = (rs, "el_adr"),
          addressJ = Address(region = (rs, "reg_u"),
            city = city,
            street = (rs, "ul_u"),
            house = (rs, "dom_u"),
            room = (rs, "kv_u"),
            postalCode = (rs, "ind_u"),
            inn = (rs, "inn_u")),
          addressF = Address(region = (rs, "reg_f"),
            city = city,
            street = (rs, "ul_f"),
            house = (rs, "dom_f"),
            room = (rs, "kv_f"),
            postalCode = (rs, "ind_f"),
            inn = (rs, "inn_f")),
          contractNumber = (rs, "kontr"),
          inn = (rs, "inn_u"),
          kpp = (rs, "kpp"),
          nameF = (rs, "naim_f"),
          phoneF = (rs, "t_f"),
          dateConclusion = (rs, "data"),
          agreementNumberGESK = (rs, "nd_gesk"),
          agreementNumberLGEK = (rs, "nd_lgek"),
          potrList = potrList
        )
    }
  }

  def readPotrForPlat(idPlat: String): List[Potr] = {
    jdbcReader.queryWithParameters(sqlPotr, HashMap("id_plat" -> idPlat)) {
      (rs, rowNum) =>
        Potr(
          address = Address(region = "",
            city = city,
            street = (rs, "ul_a"),
            house = (rs, "dom_a"),
            room = (rs, "kv_a"),
            postalCode = "",
            inn = ""),
          naimp = (rs, "naimp"),
          kelsch = (rs, "kelsch"),
          volt = (rs, "volt")
        )
    }
  }

}
