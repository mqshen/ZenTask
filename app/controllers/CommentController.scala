package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.db.slick._
import play.api.data.Form
import play.api.data.Forms._
import scala.Some
import models.{CommentDAO, TodolistDAO, MessageDAO, TodoDAO}

/**
 * Created by GoldRatio on 5/31/14.
 */
object CommentController extends Controller with Secured {

  import RepresentResult._

  val commentForm = Form(
    tuple(
      "commentableType" -> text,
      "commentableId" -> number,
      "content" -> text
    )
  )

  def doAdd(teamId: Int, projectId: Int) = IsMemberOfProject(projectId){ (user, project, s) => implicit request =>
    commentForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      commentForm => {
        DB.withSession{ implicit session =>
          val comment = CommentDAO.create(commentForm._1,  commentForm._2, commentForm._3, user.id.get, projectId, teamId)
          val content = views.html.helper.comment(teamId, comment).body.replaceAll("[\r\n]", "")
          representationOk(views.html.comment.add(comment, content), comment)
        }
      }
    )
  }

  def list(teamId: Int, projectId: Int, commentableType: String, commentableId: Int)= IsMemberOfProject(projectId){ (user, project, s) => implicit request =>
    val comments = CommentDAO.findByCommentableTypeAndId(commentableType, commentableId)(s)
    representationOk(views.html.comment.list(teamId, project, comments), comments)
  }
}
