package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.db.slick._
import models.{UserDAO, ProjectDAO}

/**
 * Created by GoldRatio on 5/19/14.
 */
object ProjectController extends Controller with Secured {

  import RepresentResult._

  def detail(teamId: Int, projectId: Int) = IsMemberOfProject(projectId) { (user, project, s) => implicit request =>
    representationOk(views.html.project.detail(teamId, project), project)
  }

  def member(teamId: Int, projectId: Int) = IsMemberOfProject(projectId) { (user, project, s) => implicit request =>
    val members = UserDAO.findUsersByProjectId(projectId)(s)
    representationOk(views.html.project.member(teamId, project, members), members)
  }

}
