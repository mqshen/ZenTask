package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick._
import models.{ProjectDAO, CalendarDAO, CommentDAO}
import play.api.Play.current

/**
 * Created by GoldRatio on 6/6/14.
 */
object CalendarController extends Controller with Secured {

  import RepresentResult._

  def list(teamId: Int) = IsMemberOf(teamId) { (user, s) => implicit request =>
    val projects = ProjectDAO.findByTeamId(teamId)(s)
    val calendars = CalendarDAO.findByTeamId(teamId)(s)
    representationOk(views.html.calendar.list(teamId, projects, calendars), teamId)
  }

  val calendarForm = Form(
    tuple(
      "name" -> text,
      "color" -> text
    )
  )

  def doAdd(teamId: Int) = IsMemberOf(teamId){ (user,s) => implicit request =>
    calendarForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      commentForm => {
          val calendar = CalendarDAO.create(commentForm._1,  commentForm._2, user.id.get, teamId)(s)
          val content = views.html.helper.calendar(teamId, calendar).body.replaceAll("[\r\n]", "")
          representationOk(views.html.calendar.add(calendar, content), calendar)
      }
    )
  }

}
