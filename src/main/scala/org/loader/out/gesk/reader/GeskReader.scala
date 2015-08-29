package org.loader.out.gesk.reader

import java.sql.ResultSet

import grizzled.slf4j.Logging
import org.loader.out.gesk.objects._
import org.loader.reader.JDBCExtractorSafe._
import org.loader.reader.JdbcTemplatesUtl._
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.language.reflectiveCalls

object GeskReader extends Logging{

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  final val city = "Липецк"

  final val sqlPlatAll = "select * from v_gesk_plat"// where id_plat = '1020'"
  final val sqlPotrAll = "select * from v_gesk_potr"
  final val sqlPerAll = "select * from v_gesk_per"


  final val sqlListLoadedPer = "select distinct t.per_id as per_id from cm_log_per_gesk_juridical t"
  final val sqlListLoadedSp = "select distinct t.sp_id as sp_id from cm_log_per_gesk_juridical t where t.sa_id is null"

  def readLoadedPerson: List[String] = {
    jdbcReader.query(sqlListLoadedPer) {
      (rs, rowNum) => (rs,"per_id")
    }
  }

  def readLoadedSp: List[String] = {
    jdbcReader.query(sqlListLoadedSp) {
      (rs, rowNum) => (rs,"sp_id")
    }
  }

  def fillParentList(platList:List[Plat]) = {

    // build Map child => parent
    val mParents: Map[String, List[String]] = platList.
      flatMap((plat) => plat.potrList.
      flatMap((potr) => potr.parent.childIdRecList.
      map((childIdRec) => (childIdRec, potr.idRec)))).
      groupBy(_._1).map { case (k, v) => (k, v.map(_._2)) }

    //add parent
    platList.map((plat) => plat.copy(
      potrList = plat.potrList.map(
        (potr) => potr.copy(
          parent = potr.parent.copy(
            parentIdRecList = mParents.getOrElse(potr.idRec, List.empty[String])
          )
        )
      )
    ))

  }

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
          id = Id(
            rspl = (rs,"rspl"),
            nLsh = (rs, "n_lsh")
          ),
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
          finance = Finance(
            nb = (rs,"bank_nb"),
            naimb = (rs,"bank_naimb"),
            rs = (rs, "bank_rs"),
            saldo = (rs, "ob_saldo")
          ),
          rs = Rs(
            bank = (rs,"x_bank"),
            rasSch = (rs,"x_ras_sch"),
            bik = (rs,"x_bik"),
            korSch = (rs,"x_kor_sch")
          ),
          phone = Phone(
            tel1 = (rs,"tel1"),
            tel2 = (rs,"tel2"),
            tel3 = (rs,"tel3")
          ),
          potrList = List.empty[Potr],
          perList = List.empty[Per]
        )
    }

    def filterPotr(p:Potr) = p.tar.cK match {
      case Some(ck) if ck == "-без УПП и СбН=n3" => false
      case _ => true
    }


    val potrMap = readAllPotr.filter((p) => filterPotr(p)).groupBy((p) => p.idPlat)

    val perMap = readAllPer.groupBy(_.idPlat)

    //load potr for plat (+ parent list)
    fillParentList(
      platList.map((plat) => plat.copy(
        potrList = fillListChildIdRec(potrMap.getOrElse(plat.idPlat, List.empty[Potr])).
          //filter shared point (parent_id_rec)
          filter((p) => p.parent.parentIdRec.isEmpty),
        perList = perMap.getOrElse(plat.idPlat,List.empty[Per])
      ))
    )

  }

  def getNearestPotr(potr:Potr,potrList:List[Potr]):Option[Potr] = {

    potrList.
      filter(_.idObj == potr.idObj).
      filter(_.id > potr.id).
      filter(_.naimp.take(1) != "-").
      sortBy (_.id).headOption match {
      case Some(a) => Some(a)
      case None => {
        info("****** NOT_FOUND " + potr.idRec + "~" + potr.idPlat + "~" + potr.idObj + "~" + potr.id)
        None
      }
    }
  }

  def createTuple(potrOpt:Option[Potr],idRecOpt:Option[String]):Option[(String,String)] = {
    for {
      potr <- potrOpt
      idRec <- idRecOpt
    } yield (potr.idRec,idRec)
  }
  
  def fillListChildIdRec(potrList: List[Potr]) = {
    val m:Map[String,List[String]] = potrList.filter((p) => !p.parent.parentIdRec.isEmpty).
      flatMap((p) => createTuple(getNearestPotr(p,potrList),p.parent.parentIdRec)).
      groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}

    potrList.map((p) => p.copy(parent = p.parent.copy(childIdRecList = m.getOrElse(p.idRec,List.empty[String]))))

  }


  def buildIdGroup(idObj: String, optIdGrup: Option[String]) = optIdGrup match {
    case Some(idGrup) => Some(s"$idObj~$idGrup")
    case _ => None
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
        d1 = (rs,"d1"),
        d3 = (rs,"d3"),
        d5 = (rs,"d5"),
        mkd = (rs,"mkd")
      ),
      tar = Tar(
        sn = (rs,"tar_sn"),
        gr = (rs,"tar_gr"),
        prim = (rs,"tar_prim"),
        znJ = (rs,"tar_zn_j"),
        cK = (rs,"c_k"),
        cSw1 = (rs,"C_SW1"),
        cSw1Sr = (rs,"C_SW1_SR"),
        cSw1Us = (rs,"C_SW1_US"),
        cSw1N = (rs,"C_SW1_N"),
        cSw1In = (rs,"C_SW1_IN"),
        c = (rs, "C")
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
      parent = Parent(
        idRecI = (rs, "id_rec_i"),
        parentIdRec = (rs,"parent_id_rec"),
        iChS = (rs,"i_ch_s"),
        chGuk = (rs,"ch_guk"),
        iNOb = (rs,"i_n_ob")
      ),
      k = K(
        k1 = (rs,"k1"),
        k2 = (rs,"k2"),
        k3 = (rs,"k3"),
        k4 = (rs,"k4"),
        kZ = (rs,"kz")),
      t = (rs,"t"),
      grpt46 = (rs,"grptr46"),
      zone = Zone(
        iZn = (rs,"i_zn"),
        idGrup = buildIdGroup(idObj =  (rs,"id_ob"), optIdGrup = (rs,"id_grup"))),
      iPch = (rs,"i_pch")
    )
  }

  def rsToPer(rs:ResultSet, rowNum: Int):Per = {
    Per(
      idPlat = (rs,"id_plat"),
      idObj = (rs,"id_ob"),
      r = (rs,"r"),
      s = (rs,"s"),
      nds = (rs,"nds"),
      mes = (rs,"mes"),
      god = (rs,"god"),
      kp = (rs,"kp"),
      effdt = (rs,"effdt")
    )
  }

  def readAllPotr: List[Potr] = {
    jdbcReader.query(sqlPotrAll)(rsToPotr)
  }

  def readAllPer:List[Per] = {
    jdbcReader.query(sqlPerAll)(rsToPer)
  }


}
