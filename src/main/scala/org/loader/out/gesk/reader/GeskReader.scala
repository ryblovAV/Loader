package org.loader.out.gesk.reader

import org.loader.out.gesk.objects.{Potr, Address, Plat}
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import org.loader.reader.JdbcTemplatesUtl._

object GeskReader {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  final val city = "Липецк"

  final val sqlPlat = "select * from v_gesk_plat where id_plat = '1001'"

  final val sqlPotr = "select * from v_gesk_potr where id_plat = :id_plat"

  def readPlat: List[Plat] = {
    jdbcReader.query(sqlPlat) {
      (rs, rowNum) => Plat(id = rs.getString("id_plat"),
        email = rs.getString("el_adr"),
        addressJ = Address(region = rs.getString("reg_u"),
          city = city,
          street = rs.getString("ul_u"),
          house = rs.getString("dom_u"),
          room = rs.getString("kv_u"),
          postalCode = rs.getString("ind_u"),
          inn = rs.getString("inn_u")),
        addressF = Address(region = rs.getString("reg_f"),
          city = city,
          street = rs.getString("ul_f"),
          house = rs.getString("dom_f"),
          room = rs.getString("kv_f"),
          postalCode = rs.getString("ind_f"),
          inn = rs.getString("inn_f")),
        contractNumber = rs.getString("kontr"),
        inn = rs.getString("inn_u"),
        kpp = rs.getString("kpp"),
        nameF = rs.getString("naim_f"),
        phoneF = rs.getString("t_f"),
        dateConclusion = rs.getDate("data"),
        agreementNumberGESK = rs.getString("nd_gesk"),
        agreementNumberLGEK = rs.getString("nd_lgek")
      )
    }
  }

  def readPotrForPlat(idPlat: String) = {
    jdbcReader.query(sqlPotr) {
      (rs, rowNum) =>
        Potr(
          address = Address(region = "",
                            city = city,
                            street = rs.getString("ul_a"),
                            house = rs.getString("dom_a"),
                            room = rs.getString("kv_a"),
                            postalCode = rs.getString(""),
                            inn = rs.getString("")),
          naimp = rs.getString("naimp"),
          kelsch = rs.getString("kelsch"),
          volt = rs.getString("volt")
        )
    }
  }

}
