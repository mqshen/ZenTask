package models

/**
 * Created by GoldRatio on 6/8/14.
 */
import java.sql.{Timestamp, Date, Time}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import com.fasterxml.jackson.annotation.JsonIgnore

case class Event ( id: Option[Int],
                      targetType: String,
                      targetId: Int,
                      name: String,
                      startTime: Option[Time],
                      description: Option[String],
                      startDate: Date,
                      endDate: Date,
                      createTime: Timestamp,
                      modifyTime: Option[Timestamp],
                      creatorId: Int,
                      teamId: Int
                      ) {
  lazy val creator = {
    DB.withSession{ implicit s =>
      UserDAO.findById(creatorId).get
    }
  }

  lazy val target:Color = {
    DB.withSession{ implicit s =>
      targetType match {
        case "Calendar" =>
          CalendarDAO.findById(targetId).get
        case "Project" =>
          ProjectDAO.findById(targetId).get
      }
    }
  }
}

class EventTable(tag: Tag) extends Table[Event](tag, "event") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def targetType = column[String]("target_type")
  def targetId = column[Int]("target_id")
  def name = column[String]("name")
  def startTime = column[Option[Time]]("start_time")
  def description = column[Option[String]]("description")
  def startDate = column[Date]("start_date")
  def endDate = column[Date]("end_date")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def creatorId = column[Int]("creator_id")
  def teamId = column[Int]("team_id")

  def * = (id, targetType, targetId, name, startTime, description, startDate, endDate, createTime, modifyTime, creatorId, teamId) <> (Event.tupled, Event.unapply _)
}

object EventDAO {

  val events = TableQuery[EventTable]

  def create(Event: Event)(implicit s: Session) {
    events.insert(Event)
  }

  def create(targetType: String, targetId: Int, name: String, startTime: Option[Time], description: Option[String],
             startDate: Date, endDate: Date, userId: Int, teamId: Int)
            (implicit s: Session): Event = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val event = new Event(None, targetType, targetId, name, startTime, description, startDate, endDate,
      currentTime, None, userId, teamId)

    val id = (events returning events.map(_.id)) += (event)
    event.copy(id = id)
  }

  def findById(id: Int)(implicit s: Session): Option[Event] =
    events.where(_.id === id).firstOption

  def findByTeamId(teamId: Int, startDate: Date, endDate: Date)(implicit s: Session): List[Event] = {
    events.where(_.teamId === teamId).where(_.startDate >= startDate).where(_.startDate <= endDate).list()
  }

}
