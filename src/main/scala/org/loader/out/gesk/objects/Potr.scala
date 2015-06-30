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
              dataSh: Option[java.util.Date])

case class Tar(sn: Option[String],
               gr: Option[String],
               prim: Option[String],
               znJ: Option[String])

case class Zone(iZn: Option[String],
                idGrup: Option[Int],
                listZonePotr: List[Potr] = List.empty[Potr])

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
                 idRecI : String,
                 k1: Option[String],
                 t: Option[String],
                 grpt46: Option[String],
                 saldo: Option[Double],
                 parentIdRec: Option[String],
                 zone: Zone) {

  def filterGw:Option[Int] = mt.gw match {
    case Some(year) if ((year > 1900) && (year <= 2016)) => Some(year)
    case _ => None
  }

  def getDateFromGw:Option[java.util.Date] = filterGw match {
    case Some(year) => Some(DBUtl.getDate(year,1,1))
    case _ => None
  }

  def isMultiZone = zone.listZonePotr.isEmpty == false

}
