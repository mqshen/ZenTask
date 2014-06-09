package controllers

/**
 * Created by GoldRatio on 6/8/14.
 */
import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick._
import models._
import play.api.Play.current
import java.util.Date
import java.text.SimpleDateFormat

/**
 * Created by GoldRatio on 6/6/14.
 */
object EventController extends Controller with Secured {

  import RepresentResult._

  def list(teamId: Int, startDate: String, endDate: String) = IsMemberOf(teamId) { (user, s) => implicit request =>
    val format = new SimpleDateFormat("yyyy-MM-dd")
    val sqlStartDate = new java.sql.Date(format.parse(startDate).getTime())
    val sqlEndDate = new java.sql.Date(format.parse(endDate).getTime())
    val events = EventDAO.findByTeamId(teamId, sqlStartDate, sqlEndDate)(s)
    val todos = TodoDAO.findByTeamId(teamId, sqlStartDate, sqlEndDate)(s)
    representationOk(views.html.event.list(teamId, events, todos), Map("events" -> events, "todos" -> todos))
  }

  val eventForm = Form(
    tuple(
      "name" -> text,
      "startTime" -> optional(date("hh:mm")),
      "description" -> optional(text),
      "bucket" -> text,
      "startDate" -> date,
      "endDate" -> date
    )
  )

  def doAdd(teamId: Int) = IsMemberOf(teamId){ (user,s) => implicit request =>
    eventForm.bindFromRequest.fold(
      formWithErrors =>
        representationNotFound(),
      eventForm => {
        val targets = eventForm._4.split(":")
        val targetType = targets(0)
        val targetId = targets(1).toInt
        val startDate = new java.sql.Date(eventForm._5.getTime)
        val endDate = new java.sql.Date(eventForm._6.getTime)
        val startTime = eventForm._2.map { time =>
          new java.sql.Time(time.getTime)
        }
        val event = EventDAO.create(targetType, targetId, eventForm._1,  startTime, eventForm._3,
          startDate, endDate, user.id.get, teamId)(s)
        val content = views.html.helper.event(teamId, event).body.replaceAll("[\r\n]", "")
        representationOk(views.html.event.add(event, content), event)
      }
    )
  }

}