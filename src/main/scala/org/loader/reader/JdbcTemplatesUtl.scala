package org.loader.reader

import java.sql.ResultSet

import grizzled.slf4j.Logging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

import scala.collection.JavaConverters._

trait JdbcTemplatesUtl extends Logging {

  protected var jdbcTemplate:NamedParameterJdbcTemplate

  def query(sql:String) = new {
    def apply[T](f: (ResultSet, Int) => T)
                (implicit ev : ((ResultSet, Int) => T) => RowMapper[T]):List[T]
    = jdbcTemplate.query(sql, ev(f)).asScala.toList
  }

  def queryWithParameters(sql:String,parameters:java.util.Map[String,Object]) = new {

    def apply[T](f: (ResultSet, Int) => T)
                (implicit ev : ((ResultSet, Int) => T) => RowMapper[T]):List[T]
    = jdbcTemplate.query(sql, parameters, ev(f)).asScala.toList
  }

  def insert(sql:String,paramMap:java.util.Map[String,_]) = {
    jdbcTemplate.update(sql,paramMap)
  }

  def insertBatch(sql:String,paramMapArray:List[java.util.Map[String,_]]) = {
    jdbcTemplate.batchUpdate(sql,paramMapArray.toArray)
  }

}

object JdbcTemplatesUtl {

  implicit def mapRow[T](rowMapper : (ResultSet, Int) => T):RowMapper[T] =
    new RowMapper[T] {
      override def mapRow(rs: ResultSet, rowNum: Int): T = rowMapper(rs,rowNum)
    }

}
