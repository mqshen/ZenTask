package models

/**
 * Created by GoldRatio on 5/31/14.
 */


import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import com.fasterxml.jackson.annotation.JsonIgnore

case class Comment ( id: Option[Int],
                     commentableType: String,
                     commentableId: Int,
                     content: String,
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

class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def commentableType = column[String]("commentable_type")
  def commentableId = column[Int]("commentable_id")
  def content = column[String]("content")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def creatorId = column[Int]("creator_id")
  def projectId = column[Int]("project_id")
  def teamId = column[Int]("team_id")

  def * = (id, commentableType, commentableId, content, createTime, modifyTime, creatorId, projectId, teamId) <> (Comment.tupled, Comment.unapply _)
}

object CommentDAO {

  val comments = TableQuery[CommentTable]

  def create(comment: Comment)(implicit s: Session) {
    comments.insert(comment)
  }

  def create(commentableType: String, commentableId: Int, connent: String, ownId: Int, projectId: Int, teamId: Int)
            (implicit s: Session): Comment = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val comment = new Comment(None, commentableType, commentableId, connent, currentTime, None, ownId, projectId, teamId)

    val id = (comments returning comments.map(_.id)) += (comment)
    comment.copy(id = id)
  }


  def findById(id: Int)(implicit s: Session): Option[Comment] =
    comments.where(_.id === id).firstOption

  def findByTeamId(teamId: Int)(implicit s: Session): List[Comment] = {
    comments.where(_.teamId === teamId).list()
  }

  def findByCommentableTypeAndId(commentableType: String, commentableId: Int)(implicit s: Session): List[Comment] = {
    comments.where(_.commentableType ===  commentableType).where(_.commentableId === commentableId).list()
  }

}