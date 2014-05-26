package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.db.slick._
import models.{TodolistDAO, TodoDAO, ProjectDAO}
import play.api.data.Form
import play.api.data.Forms._

/**
 * Created by GoldRatio on 5/22/14.
 */
object TodolistController extends Controller {

  import RepresentResult._

  val todolistForm = Form(
    tuple(
      "name" -> text,
      "description" -> optional(text)
    )
  )

  def index(teamId: Int) = Action {implicit request =>
    DB.withSession { implicit s =>
      val projects = ProjectDAO.findByTeamId(teamId)
      representationOk(views.html.project.list(teamId, projects), projects)
    }
  }

  def detail(teamId: Int, projectId: Int) = Action { implicit request =>
    DB.withSession { implicit s =>
      ProjectDAO.findById(projectId).map{ project =>
        representationOk(views.html.project.detail(teamId, project), project)
      }.getOrElse {
        representationNotFound()
      }
    }
  }


  def doAdd(teamId: Int, projectId: Int) = Action { implicit request =>
    todolistForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      todolistForm => {
        DB.withSession{ implicit session =>
          val todolist = TodolistDAO.create(projectId, todolistForm._1)
          val content = views.html.helper.todolist(teamId, projectId, todolist).body.replaceAll("[\r\n]", "")
          representationOk(views.html.todolist.add(todolist, content), todolist)
        }
      }
    )
  }

  def doModify(teamId: Int, projectId: Int, todolisId: Int) = Action { implicit request =>
    todolistForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      todolistForm => {
        DB.withSession{ implicit session =>
          TodolistDAO.doModify(todolisId, todolistForm._1, todolistForm._2).map{todolist =>
            val content = views.html.helper.todolist(teamId, projectId, todolist).body.replaceAll("[\r\n]", "")
            representationOk(views.html.todolist.update(todolist, content), todolist)
          }.getOrElse(representationNotFound())
        }
      }
    )
  }
}
