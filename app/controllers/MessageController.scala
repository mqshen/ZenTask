package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.db.slick._
import play.api.data.Form
import play.api.data.Forms._
import scala.Some
import models.{MessageDAO, TodoDAO}

/**
 * Created by GoldRatio on 5/24/14.
 */
object MessageController extends Controller with Secured {

  import RepresentResult._

  def add(teamId: Int, projectId: Int) = IsMemberOfProject(projectId) { (user, project, s) => implicit request =>
    Ok(views.html.topic.add(teamId, project))
  }

  val messageForm = Form(
    tuple(
      "subject" -> text,
      "content" -> text
    )
  )

  def doAdd(teamId: Int, projectId: Int) = IsMemberOfProject(projectId) { (user, project, s) => implicit request =>
    messageForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      messageForm => {
        DB.withSession{ implicit session =>
          val summary = messageForm._2.replaceAll("\\<[^>]*>","")

          val message = MessageDAO.create(messageForm._1,  messageForm._2,
            summary.substring(0, math.min(50, summary.length)), user.id.get,  projectId)
          message.id.map { id =>
            representationOk(views.html.topic.addSuccess(teamId, project, message), message)
          }.getOrElse(BadRequest)
        }
      }
    )
  }

  def detail(teamId: Int, projectId: Int, messageId: Int) = IsMemberOfProject(projectId) { (user, project, s) => implicit request =>
    MessageDAO.findById(messageId)(s).map { message =>
      representationOk(views.html.topic.detail(teamId, project, message), message)
    }.getOrElse(NotFound)
  }

}
