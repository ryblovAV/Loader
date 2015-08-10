package org.loader.writer

import play.api.libs.json._
import java.util.{Map => JMap}

import scala.collection.JavaConversions._

object JsonLogWriter {

  def mapToJson(m: JMap[String,String]):JsObject = {

    JsObject(
      m.toSeq.map {
        case(name,value) => (name,JsString(value))
      }
    )
  }



}
