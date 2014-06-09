package models

/**
 * Created by GoldRatio on 6/7/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import com.fasterxml.jackson.annotation.JsonIgnore

case class Calendar ( id: Option[Int],
                     name: String,
                     color: String,
                     createTime: Timestamp,
                     modifyTime: Option[Timestamp],
                     creatorId: Int,
                     teamId: Int
                     ) extends Color {
  lazy val creator = {
    DB.withSession{ implicit s =>
      UserDAO.findById(creatorId).get
    }
  }
}

class CalendarTable(tag: Tag) extends Table[Calendar](tag, "calendar") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def color = column[String]("color")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def creatorId = column[Int]("creator_id")
  def teamId = column[Int]("team_id")

  def * = (id, name, color, createTime, modifyTime, creatorId, teamId) <> (Calendar.tupled, Calendar.unapply _)
}

object CalendarDAO {

  val Calendars = TableQuery[CalendarTable]

  def create(Calendar: Calendar)(implicit s: Session) {
    Calendars.insert(Calendar)
  }

  def create(name: String, color: String, userId: Int, teamId: Int)
            (implicit s: Session): Calendar = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val Calendar = new Calendar(None, name, color, currentTime, None, userId, teamId)

    val id = (Calendars returning Calendars.map(_.id)) += (Calendar)
    Calendar.copy(id = id)
  }

  def findById(id: Int)(implicit s: Session): Option[Calendar] =
    Calendars.where(_.id === id).firstOption

  def findByTeamId(teamId: Int)(implicit s: Session): List[Calendar] = {
    Calendars.where(_.teamId === teamId).list()
  }

}