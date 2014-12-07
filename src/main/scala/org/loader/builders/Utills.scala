package org.loader.builders

import java.text.SimpleDateFormat

import org.loader.db.utl.DBUtl
import org.loader.out.lesk.objects.Client

object Utills {


  implicit def dateToStr(dt:java.util.Date) = {
    val df = new SimpleDateFormat("dd.MM.yyyy")
    df.format(dt)
  }

  def getDefaultDt = DBUtl.getDate(1950, 1, 1)

  def getEnvId = 1

  def getPerId = "9123456789"
  def getAcctId = "1"
  def getAcctApayId = "1"



}
