package org.loader.builders

import org.loader.reader.JdbcTemplatesUtl._
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext

object Keys {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])


  val sql = "select leskdata.pkg_cm_load_juridical_keys.%s(:codeBase) as key from dual"

  def getKey(functionName:String, codeBase:String) = {

    val parameters = new java.util.HashMap[String, Object]()
    parameters.put("codeBase", codeBase)

    val keyList = jdbcReader.queryWithParameters(sql.format(functionName), parameters) {
      (rs, rowNum) => rs.getString("key")
    }

    keyList(0)
  }


  def spId(codeBase:String):String = getKey("sp_id",codeBase)

}
