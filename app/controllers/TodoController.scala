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
object TodoController extends Controller with Secured {

  import RepresentResult._

  val assignForm = Form(
    tuple(
      "description" -> optional(text),
      "workerId" -> optional(number),
      "deadline" -> optional(date)
    )
  )

  val todoForm = Form(
    tuple(
      "todolistId" -> number,
      "description" -> text,
      "workerId" -> optional(number),
      "deadline" -> optional(date)
    )
  )

//  def index(teamId: Int) = Action {implicit request =>
//    DB.withSession { implicit s =>
//      val projects = ProjectDAO.findByTeamId(teamId)
//      representationOk(views.html.project.list(teamId, projects), projects)
//    }
//  }

  def detail(teamId: Int, projectId: Int, todoId: Int) = IsMemberOfProject(projectId){ (user, project, s) => implicit request =>
    TodoDAO.findById(todoId)(s).map{ todo =>
      representationOk(views.html.todo.detail(teamId, project, todo, user), todo)
    }.getOrElse {
      representationNotFound()
    }
  }

  def doAdd(teamId: Int, projectId: Int) = IsMemberOfProject(projectId){ (user, project, s) => implicit request =>
    todoForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      todoForm => {
        DB.withSession{ implicit session =>
          val date: Option[java.sql.Date] = todoForm._4.map { date =>
            Some(new java.sql.Date(date.getTime))
          }.getOrElse(None)
          val todo = TodoDAO.create(todoForm._1,  todoForm._2, user.id.get, todoForm._3, date, projectId, teamId)
          TodolistDAO.uncompleteTodo(todo.listId)(s)
          val content = views.html.helper.todo(teamId, todo).body.replaceAll("[\r\n]", "")
          representationOk(views.html.todo.add(todo, content), todo)
        }
      }
    )
  }


  def doModify(teamId: Int, projectId: Int, todoId: Int) = Action { implicit request =>
    assignForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      assign => {
        DB.withSession{ implicit session =>
          val date: Option[java.sql.Date] = assign._3.map { date =>
            Some(new java.sql.Date(date.getTime))
          }.getOrElse(None)
          TodoDAO.update(todoId, assign._1, assign._2, date).map{ todo =>
            val content = views.html.helper.todo(teamId, todo).body.replaceAll("[\r\n]", "")
            representationOk(views.html.todo.update(todoId, content), todo)
          }.getOrElse(representationNotFound())
        }
      }
    )
  }

  def toggle(teamId: Int, projectId: Int, todoId: Int, flag: Boolean) = IsMemberOfTodo(todoId){ (user, s)=>
    implicit request =>


      TodoDAO.update(todoId, flag, user.id)(s).map{ todo =>
        val content = if(flag) {
          TodolistDAO.completeTodo(todo.listId)(s)
          views.html.helper.todoComplete(teamId, projectId, todo).body.replaceAll("[\r\n]", "")
        }
        else {
          TodolistDAO.uncompleteTodo(todo.listId)(s)
          views.html.helper.todo(teamId, todo).body.replaceAll("[\r\n]", "")
        }
        representationOk(views.html.todo.toggle(todo, content), todo)
      }.getOrElse(representationNotFound())

  }

  def edit(teamId: Int, projectId: Int, todoId: Int) = IsMemberOfTodo(todoId){ (user, s) => implicit request =>
    DB.withSession{ implicit session =>
      TodoDAO.findById(todoId)(s).map{ todo =>
        val content = views.html.helper.todoEdit(teamId, projectId, todo).body.replaceAll("[\r\n]", "")
        representationOk(views.html.todo.edit(todoId, content), todo)
      }.getOrElse(representationNotFound())
    }
  }

  def doDelete(teamId: Int, projectId: Int, todoId: Int) = Action { implicit request =>
    DB.withSession{ implicit session =>
      TodoDAO.delete(todoId)
      representationOk(views.html.todo.delete(todoId) , todoId)
    }
  }

}