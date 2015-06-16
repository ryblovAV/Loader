package org.loader.out.gesk.objects

import org.loader.db.utl.DBUtl

case class Mt(volt: Option[String],
              amper: Option[String],
              klast: Option[String],
              gw: Option[Int],
              mIn: Option[String],
              dp: Option[String],
              tip: String,
              pLi: Option[String],
              pTr: Option[String],
              dataSh: Option[java.util.Date])

case class  Potr(naimp: String,
                 kelsch: String,
                 nelsch: Option[String],
                 idObj:String,
                 address: Address,
                 mt: Mt,
                 rks: Double,
                 r2: Double,
                 data: java.util.Date,
                 data2: java.util.Date,
                 kniga: Option[String],
                 gp:Option[String],
                 kp: Option[String],
                 idRec: String,
                 k1: Option[String]) {

  def filterGw:Option[Int] = mt.gw match {
    case Some(year) if ((year > 1900) && (year <= 2016)) => Some(year)
    case _ => None
  }

  def getDateFromGw:Option[java.util.Date] = filterGw match {
    case Some(year) => Some(DBUtl.getDate(year,1,1))
    case _ => None
  }

}
