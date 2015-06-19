package org.loader.reader

import java.sql.ResultSet
import java.util.Date

object JDBCExtractorSafe {

  def extractOptValue[T](p:(ResultSet,String),f:(ResultSet,String) => T):Option[T] = p match {
    case (rs,name) => {
      val v = f(rs,name)
      if ((rs.wasNull) || (v == "")) None else Some(v)
    }
  }

  def extractValue[T](p:(ResultSet,String),f:(ResultSet,String) => T):T = p match {
    case (rs,name) => f(rs,name)
  }

  implicit def rsToOptInt(p:(ResultSet,String)):Option[Int] = extractOptValue(p,(rs,name) => rs.getInt(name))

  implicit def rsToInt(p:(ResultSet,String)):Int = extractValue(p,(rs,name) => rs.getInt(name))

  implicit def rsToOptDouble(p:(ResultSet,String)):Option[Double] = extractOptValue(p,(rs,name) => rs.getDouble(name))

  implicit def rsToDouble(p:(ResultSet,String)):Double = extractValue(p,(rs,name) => rs.getDouble(name))

  implicit def rsToOptString(p:(ResultSet,String)):Option[String] = extractOptValue(p,(rs,name) => rs.getString(name))

  implicit def rsToString(p:(ResultSet,String)):String = extractValue(p,(rs,name) => rs.getString(name))

  implicit def rsToOptDate(p:(ResultSet,String)):Option[Date] = extractOptValue(p,(rs,name) => rs.getDate(name))

  implicit def rsToDate(p:(ResultSet,String)):Date = extractValue(p,(rs,name) => rs.getDate(name))

}
