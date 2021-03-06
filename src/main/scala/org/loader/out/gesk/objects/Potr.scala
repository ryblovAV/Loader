package org.loader.out.gesk.objects

import org.loader.db.utl.DBUtl

case class Mt(volt: Option[String],
              amper: Option[String],
              klast: Option[String],
              ustM: Option[Double],
              gw: Option[Int],
              mIn: Option[String],
              dp: Option[String],
              tip: Option[String],
              pLi: Option[Double],
              pTr: Option[Double],
              rks: Double,
              r1: Option[Double],
              r2: Option[Double],
              d1: Option[Double],
              d3: Option[Double],
              d5: Option[Double],
              dataSh: Option[java.util.Date],
              mkd: Option[Double]) {

  def isHistVol = d1.getOrElse(0) != 0 && d3.getOrElse(0) != 0 && d5.getOrElse(0) != 0
}



case class Tar(sn: Option[String],
               gr: Option[String],
               prim: Option[String],
               znJ: Option[String],
               cK: Option[String],
               cSw1: Option[Double],
               cSw1Sr: Option[Double],
               cSw1Us: Option[Double],
               cSw1N: Option[Double],
               cSw1In: Option[Double],
               c: Option[Double])

case class Zone(iZn: Option[String],
                idGrup: Option[String],
                listZonePotr: List[Potr] = List.empty[Potr])

case class Parent(idRecI: Option[String],
                  parentIdRec: Option[String],
                  iChS: Option[String],
                  chGuk: Option[String],
                  iNOb: Option[String],
                  parentIdRecList: List[String] = List.empty[String],
                  childIdRecList: List[String] = List.empty[String]) {



  def existsParent:Boolean = (!idRecI.isEmpty) || (!parentIdRecList.isEmpty)

}

case class K(k1: Double,
             k2: Double,
             k3: Double,
             k4: Double,
             kZ: Double) {
  def isFill = !((k1 == 0) || (k2 == 0) || (k3 == 0) || (k4 == 0) || (kZ == 0))
}

case class  Potr(id: Int,
                 idPlat: String,
                 naimp: String,
                 kelsch: String,
                 nelsch: Option[String],
                 idObj:String,
                 address: Address,
                 mt: Mt,
                 tar: Tar,
                 data2: java.util.Date,
                 kniga: Option[String],
                 gp:Option[String],
                 kp: Option[String],
                 idRec: String,
                 parent: Parent,
                 k: K,
                 t: Option[String],
                 grpt46: Option[String],
                 zone: Zone,
                 iPch: String) {

  def filterGw:Option[Int] = mt.gw match {
    case Some(year) if ((year > 1900) && (year <= 2016)) => Some(year)
    case _ => None
  }

  def getDateFromGw:Option[java.util.Date] = filterGw match {
    case Some(year) => Some(DBUtl.getDate(year,1,1))
    case _ => None
  }

  def isMultiZone = zone.listZonePotr.isEmpty == false

  def isInterval = iPch == "true"

  def isMkd = ((mt.r1.getOrElse(0) == 0) && (mt.r2.getOrElse(0) != 0))

  def isOrphan = ((parent.iChS.getOrElse("0") != "0" ) || (!parent.chGuk.isEmpty)) &&
    //Ранее использовалось условие
    //(potr.parent.chGuk.getOrElse(" ")  == "*") &&
    (naimp.take(1) == "-") &&
    (parent.existsParent == false)

}
