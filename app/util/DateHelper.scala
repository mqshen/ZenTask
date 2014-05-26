package util

import java.util.{Calendar, Date}
import java.sql.Timestamp

/**
 * Created by GoldRatio on 5/22/14.
 */
object DateHelper {
  val format = new java.text.SimpleDateFormat("M月d日 E")
  val simpleFormat = new java.text.SimpleDateFormat("M月d日")

  def formDate(date: Date): String = {
    format.format(date)
  }

  def formDateSimple(date: Date): String = {
    simpleFormat.format(date)
  }

  def formDateSimple(date: Option[java.sql.Date]): String = {
    date.map{ date =>
      simpleFormat.format(date)
    }.getOrElse("")
  }

  def formTimestamp(timeStamp: Timestamp): String = {
    val date = new Date(timeStamp.getTime)
    simpleFormat.format(date)
  }

  def todayOrBefore(date: java.sql.Date): Boolean = {
    date.getTime <= new Date().getTime
  }
}
