package org.loader.builders

import java.text.SimpleDateFormat

import org.loader.db.utl.DBUtl

object Utills {


  implicit def dateToStr(dt: java.util.Date): String = {
    val df = new SimpleDateFormat("dd.MM.yyyy")
    df.format(dt)
  }

  def getDefaultDt = DBUtl.getDate(1950, 1, 1)

  def getEnvId = 1

  def getPerId = "9123456789"
  def getAcctId = "1"
  def getAcctApayId = "1"

  def getPremId = "1"



}
