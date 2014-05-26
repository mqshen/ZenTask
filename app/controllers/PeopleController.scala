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
 *
 * Created by GoldRatio on 5/25/14.
 */
object PeopleController extends Controller with Secured {

  import RepresentResult._

  def me(teamId: Int) = IsAuthenticated { user => request =>
    DB.withSession { implicit s =>
      val todos = TodoDAO.findUncompletedByTeamIdAndWorkerId(teamId, user.id.get)

      Ok(views.html.people.me(teamId, user, todos))

    }
  }

}
