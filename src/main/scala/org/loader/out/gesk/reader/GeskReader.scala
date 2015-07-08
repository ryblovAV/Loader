package org.loader.out.gesk.reader

import java.sql.ResultSet

import org.loader.out.gesk.objects._
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

  final val sqlPlat = "select * from v_gesk_plat where id_plat = '1002'"

  final val sqlPlatAll = "select * from v_gesk_plat /*where rownum < 100*/"

  final val sqlPlat0 =
    s"""
       |select *
       | from v_gesk_plat t
       |where rownum < 100
       |  and not exists (select *
       |                    from cm_log_per_gesk_juridical j
       |                   where j.id_plat = t.id_plat)
       |  /*and :id_plat is not null*/
     """.stripMargin


  final val sqlPotr = "select * from v_gesk_potr where id_plat = :id_plat"

  final val sqlPotrAll = "select * from v_gesk_potr"

  def readPlat: List[Plat] = {

    //jdbcReader.queryWithParameters(sqlPlat, HashMap("id_plat" -> idPlat)) {
    val platList = jdbcReader.query(sqlPlatAll) {
      (rs, rowNum) =>
        Plat(
          idPlat = (rs, "id_plat"),
          seq = (rs, "id"),
          el_adr = (rs, "el_adr"),
          addressU = Address(
            reg = (rs, "reg_u"),
            ul = (rs, "ul_u"),
            dom = (rs, "dom_u"),
            kv = (rs, "kv_u"),
            ind = (rs, "ind_u"),
            inn = (rs, "inn_u"),
            phone = (rs, "t_u"),
            abv1 = (rs, "abv1_u"),
            abv2 = (rs, "abv2_u")
          ),
          addressF = Address(
            reg = (rs, "reg_f"),
            ul = (rs, "ul_f"),
            dom = (rs, "dom_f"),
            kv = (rs, "kv_f"),
            ind = (rs, "ind_f"),
            inn = (rs, "inn_f"),
            phone = (rs, "t_f"),
            abv1 = (rs, "abv1_f"),
            abv2 = (rs, "abv2_f")),
          kontr = (rs, "kontr"),
          kpp = (rs, "kpp"),
          name = Name(
            naimF = (rs, "naim_f"),
            naimU = (rs, "naim_u")
          ),
          tF = (rs, "t_f"),
          data = (rs, "data"),
          ndGesk = (rs, "nd_gesk"),
          ndLgek = (rs, "nd_lgek"),
          ko = (rs, "ko"),
          kob = (rs, "kob"),
          kOkwed = (rs, "k_okwed"),
          bank = Bank(
            nb = (rs,"bank_nb"),
            naimb = (rs,"bank_naimb"),
            rs = (rs, "bank_rs")
          ),
          rs = Rs(
            bank = (rs,"x_bank"),
            rasSch = (rs,"x_ras_sch"),
            bik = (rs,"x_bik"),
            korSch = (rs,"x_kor_sch")
          ),
          rspl = (rs,"rspl"),
          phone = Phone(
            tel1 = (rs,"tel1"),
            tel2 = (rs,"tel2"),
            tel3 = (rs,"tel3")
          ),
          potrList = Nil
        )
    }

    val potrList = readAllPotr.groupBy((p) => p.idPlat)

    //load potr for plat
//    platList.map((plat) => plat.copy(potrList = readPotrForPlat(plat.idPlat)))
    platList.map((plat) => plat.copy(potrList = potrList.getOrElse(plat.idPlat,List.empty[Potr])))




  }

  def rsToPotr(rs:ResultSet, rowNum: Int):Potr = {
    Potr(
      address = Address(
        reg = None,
        ul = (rs, "ul_a"),
        dom = (rs, "dom_a"),
        kv = (rs, "kv_a"),
        ind = None,
        inn = None,
        abv1 = (rs,"abv_d"),
        abv2 = (rs,"abv_u"),
        phone = None
      ),
      mt = Mt(volt = (rs, "volt"),
        amper = (rs, "amper"),
        klast = (rs, "klast"),
        ustM = (rs, "ust_m"),
        gw = (rs, "gw"),
        mIn = (rs, "m_in"),
        dp = (rs, "d_p"),
        tip = (rs, "tip"),
        pLi = (rs,"p_li"),
        pTr = (rs,"p_tr"),
        dataSh = (rs,"data_sh"),
        rks = (rs, "rks"),
        r1 = (rs, "r1"),
        r2 = (rs, "r2"),
        rUz = (rs,"r_uz")
      ),
      tar = Tar(
        sn = (rs,"tar_sn"),
        gr = (rs,"tar_gr"),
        prim = (rs,"tar_prim"),
        znJ = (rs,"tar_zn_j")
      ),
      id = (rs,"id"),
      idPlat = (rs,"id_plat"),
      naimp = (rs, "naimp"),
      kelsch = (rs, "kelsch"),
      idObj = (rs,"id_ob"),
      nelsch = (rs, "nelsch"),
      data2 = (rs, "data2"),
      kniga = (rs,"kniga"),
      gp = (rs,"gp"),
      kp = (rs,"kp"),
      idRec = (rs,"id_rec"),
      idRecI = (rs,"id_rec_i"),
      k1 = (rs,"k1"),
      t = (rs,"t"),
      grpt46 = (rs,"grptr46"),
      saldo = (rs,"OB_SALDO"),
      parentIdRec = (rs,"parent_id_rec"),
      zone = Zone(
        iZn = (rs,"i_zn"),
        idGrup = (rs,"id_grup"))
    )
  }

  def readPotrForPlat(idPlat: String): List[Potr] = {
    jdbcReader.queryWithParameters(sqlPotr, HashMap("id_plat" -> idPlat))(rsToPotr)
  }

  def readAllPotr: List[Potr] = {
    jdbcReader.query(sqlPotrAll)(rsToPotr)
  }

}
