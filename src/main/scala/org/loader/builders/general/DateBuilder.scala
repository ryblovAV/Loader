package org.loader.builders.general

import java.text.{DateFormat, SimpleDateFormat}
import java.util.{Calendar, Date, GregorianCalendar}

import scala.language.implicitConversions


object DateBuilder {

  private var dateFormat: DateFormat = new SimpleDateFormat("dd/MM/yyyy")

  def getDate(year: Int, month: Int, day: Int): Date = {
    val calendar: Calendar = new GregorianCalendar
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)
    calendar.set(Calendar.DAY_OF_MONTH, day)

    return trunc(calendar).getTime
  }

  def getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Date = {
    val calendar: Calendar = new GregorianCalendar

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)
    calendar.set(Calendar.DAY_OF_MONTH, day)
    calendar.set(Calendar.HOUR_OF_DAY,hour)
    calendar.set(Calendar.MINUTE,minute)
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

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

  def addMonth(date: Date, month: Int) = {
    val calendar = Calendar.getInstance
    calendar.setTime(date)
    calendar.add(Calendar.MONTH, month)
    calendar.getTime
  }

  def trunc(calendar: Calendar) = {
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar
  }

  def lastDay(date: Date) = {
    val calendar = Calendar.getInstance
    calendar.setTime(date)
    calendar.add(Calendar.MONTH,1)
    calendar.add(Calendar.HOUR,-24)
    trunc(calendar).getTime
  }

}
