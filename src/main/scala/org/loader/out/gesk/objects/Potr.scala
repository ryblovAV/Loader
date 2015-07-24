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
              pLi: Option[String],
              pTr: Option[String],
              rks: Double,
              r1: Double,
              r2: Double,
              rUz: Option[Double],
              dataSh: Option[java.util.Date],
              mkd: Option[Double])

case class Tar(sn: Option[String],
               gr: Option[String],
               prim: Option[String],
               znJ: Option[String])

case class Zone(iZn: Option[String],
                idGrup: Option[String],
                listZonePotr: List[Potr] = List.empty[Potr])

case class Parent(idRecI: Option[String],
                  parentIdRec: Option[String],
                  iChS: Option[String],
                  chGuk: Option[String],
                  iNOb: Option[String]) {

  def getParentIdRec:Option[String] = if (idRecI.isEmpty) idRecI else parentIdRec

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
                 k1: Option[String],
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

  def isHistVol = mt.rUz.isEmpty == false

  def isInterval = iPch == "true"

  def isMkd = ((mt.r1 == 0) && (mt.r2 != 0))

}
