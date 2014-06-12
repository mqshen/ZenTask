package models

/**
 * Created by GoldRatio on 6/12/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB

case class AttachmentReference(url: String,
                               attachmentableType: String,
                               attachmentableId: Int)

class AttachmentReferenceTable(tag: Tag) extends Table[AttachmentReference](tag, "attachment_reference") {
  def url = column[String]("url")
  def targetType = column[String]("target_type")
  def targetId = column[Int]("target_id")

  def * = (url, targetType, targetId) <> (AttachmentReference.tupled, AttachmentReference.unapply _)


}

object AttachmentReferenceDAO {

  val attachmentReferences = TableQuery[AttachmentReferenceTable]

  def create(attachmentReference: AttachmentReference)(implicit s: Session) {
    attachmentReferences.insert(attachmentReference)
  }

  def create(url: String, attachmentableType: String, attachmentableId: Int)
            (implicit s: Session){
    val attachmentReference = new AttachmentReference(url, attachmentableType, attachmentableId)
    attachmentReferences.insert(attachmentReference)
  }

  def findByTargetTypeAndId(targetType: String, targetId: Int)(implicit s: Session): List[AttachmentReference] = {
    attachmentReferences.where(_.targetType ===  targetType).where(_.targetId === targetId).list()
  }
}

case class Attachment ( id: Option[Int],
                     url: String,
                     name: String,
                     createTime: Timestamp,
                     modifyTime: Option[Timestamp],
                     creatorId: Int,
                     projectId: Int,
                     teamId: Int
                     ) {
  lazy val creator = {
    DB.withSession{ implicit s =>
      UserDAO.findById(creatorId).get
    }
  }
}

class AttachmentTable(tag: Tag) extends Table[Attachment](tag, "attachment") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def url = column[String]("url")
  def name = column[String]("name")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def creatorId = column[Int]("creator_id")
  def projectId = column[Int]("project_id")
  def teamId = column[Int]("team_id")

  def * = (id, url, name, createTime, modifyTime, creatorId, projectId, teamId) <> (Attachment.tupled, Attachment.unapply _)
}

object AttachmentDAO {

  val attachments = TableQuery[AttachmentTable]

  def create(attachment: Attachment)(implicit s: Session) {
    attachments.insert(attachment)
  }

  def create(url: String, name: String, ownId: Int, projectId: Int, teamId: Int)
            (implicit s: Session): Attachment = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val attachment = new Attachment(None, url, name, currentTime, None, ownId, projectId, teamId)

    val id = (attachments returning attachments.map(_.id)) += (attachment)
    attachment.copy(id = id)
  }


  def findById(id: Int)(implicit s: Session): Option[Attachment] =
    attachments.where(_.id === id).firstOption

  def findByTeamId(teamId: Int)(implicit s: Session): List[Attachment] = {
    attachments.where(_.teamId === teamId).list()
  }

}