package org.loader.builders.general

import java.text.{DateFormat, SimpleDateFormat}
import java.util.{Calendar, Date, GregorianCalendar}

object DateBuilder {

  private var dateFormat: DateFormat = new SimpleDateFormat("dd/MM/yyyy")

  def getDate(year: Int, month: Int, day: Int): Date = {
    val calendar: Calendar = new GregorianCalendar
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)
    calendar.set(Calendar.DAY_OF_MONTH, day)
    trunc(calendar)
    return calendar.getTime
  }

  implicit def dateToStr(dt: java.util.Date): String = {
    val df = new SimpleDateFormat("dd.MM.yyyy")
    df.format(dt)
  }

  def getDefaultDt = getDate(1950, 1, 1)

  def addMinute(date: Date, minute: Int) = {
    val calendar = Calendar.getInstance
    calendar.setTime(date)
    calendar.add(Calendar.MINUTE,minute)
    calendar.getTime
  }

  def addDay(date: Date, day: Int = 1) = {
    val calendar = Calendar.getInstance
    calendar.setTime(date)
    calendar.add(Calendar.DAY_OF_MONTH, day)
    calendar.getTime
  }

  def trunc(calendar: Calendar) = {
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
  }

  def lastDay(date: Date) = {
    val calendar = Calendar.getInstance
    calendar.setTime(date)
    calendar.add(Calendar.MONTH,1)
    calendar.add(Calendar.HOUR,-24)
    trunc(calendar)

    calendar.getTime
  }

}
