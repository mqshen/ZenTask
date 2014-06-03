package models

/**
 * Created by GoldRatio on 5/24/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import util.DateHelper

case class Message ( id: Option[Int],
                     subject: String,
                  content: String,
                  summary: String,
                  creatorId: Int,
                  createTime: Timestamp,
                  modifyTime: Option[Timestamp],
                  projectId: Int,
                  deleted: Boolean = false
                  ) {

  lazy val creator = {
    DB.withSession{ implicit s =>
      UserDAO.findById(creatorId).get
    }
  }
}

class MessageTable(tag: Tag) extends Table[Message](tag, "message") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def subject = column[String]("subject")
  def content = column[String]("content")
  def summary = column[String]("summary")
  def creatorId = column[Int]("creator_id")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def projectId = column[Int]("project_id")
  def deleted = column[Boolean]("deleted")

  def * = (id, subject, content, summary, creatorId, createTime, modifyTime, projectId, deleted) <> (Message.tupled, Message.unapply _)
}

object MessageDAO {

  val messages = TableQuery[MessageTable]

  def create(Message: Message)(implicit s: Session) {
    messages.insert(Message)
  }

  def create(subject: String, content: String, summary: String, ownId: Int, projectId: Int)(implicit s: Session): Message = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val message = new Message(None, subject, content, summary, ownId, currentTime, None, projectId)
    val id = (messages returning messages.map(_.id)) += (message)
    message.copy(id = id)
  }

  def delete(id: Int)(implicit s: Session) {
    val q = for { t <- messages if t.id === id} yield (t.deleted)
    q.update(true)
  }

  def findById(id: Int)(implicit s: Session): Option[Message] = {
    messages.where(_.id === id).firstOption
  }

  def findByProjectId(projectId: Int, size: Int, page: Int = 0)(implicit s: Session): List[Message] = {
    messages.where(_.projectId === projectId).sortBy(_.createTime.desc).drop(page * size).take(size).list()
  }

}