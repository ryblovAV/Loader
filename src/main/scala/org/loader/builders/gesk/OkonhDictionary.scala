package org.loader.builders.gesk

import scala.io.Source

object OkonhDictionary {

  def read = {
    val bufferedSource = Source.fromFile("src/main/resources/dic/okonh.txt")
    val dic = bufferedSource.getLines.
        map((s)=>s.split("~")).
          foldLeft(Map.empty[String,String])((m,v)=> m + (v(0) -> v(1)))
    bufferedSource.close
    dic
  }

  val dic = read

  //TODO add exception or log error
  def getOkonh(kobOpt:Option[String]) = kobOpt match {
    case Some(kob) => dic.get(kob)
    case None      => None
  }

}
