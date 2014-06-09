package controllers

import play.api.Play.current
import play.api.mvc._
import play.api.db.slick._
import models.{ProjectDAO, TeamDAO}

/**
 * Created by GoldRatio on 5/23/14.
 */
object TeamController extends Controller with Secured {

  import RepresentResult.representationOk

  def index = IsAuthenticated { user => implicit request =>
    DB.withSession { implicit c =>
      val teams = TeamDAO.findTeamsByUserId(user.id.get)
      if(teams.size == 1) {
        Results.Redirect(routes.TeamController.detail(teams.head.id.get))
      }
      else {
        Ok(views.html.team.list(teams))
      }
    }
  }

  def detail(teamId: Int) = IsMemberOf(teamId) { (user, s) => implicit request =>
    val projects = ProjectDAO.findByTeamId(teamId)(s)
    representationOk(views.html.project.list(teamId, projects), Map("teamId" -> teamId, "projects" -> projects))
  }

}
