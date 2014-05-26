package controllers

import play.api.mvc._
import play.api.cache.Cache
import scala.Some
import models.{Project, UserProjectRelDAO, UserTeamRelDAO, User}
import play.api.Play.current
import play.api.db.slick._
import scala.slick.jdbc.JdbcBackend

/**
 * Created by GoldRatio on 5/14/14.
 */

trait Secured {

  /**
   * Retrieve the connected user email.
   */
  private def sessionId(request: RequestHeader) = {
    request.session.get("sessionId") match {
      case Some(sessionId) =>
        Cache.getAs[User](sessionId)
      case _ =>
        None
    }
  }

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.UserController.login)
  // --

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => User => Request[AnyContent] => Result) = {
    Security.Authenticated(sessionId, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  /**
   * Check if the connected user is a member of this project.
   */
  def IsMemberOf(teamId: Int)(f: => User => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    user.id.map { userId =>
      DB.withSession { implicit s =>
        if(UserTeamRelDAO.isMember(teamId, userId)) {
          f(user)(request)
        } else {
          Results.Forbidden
        }
      }
    }.getOrElse(Results.Forbidden)
  }

  def IsMemberOfProject(projectId: Int)(f: => (User, Project, JdbcBackend.Session) => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    user.id.map { userId =>
      DB.withSession { implicit s =>
        UserProjectRelDAO.isMember(projectId, userId).map { project =>
          f(user, project, s)(request)
        }.getOrElse {
          Results.Forbidden
        }
      }
    }.getOrElse(Results.Forbidden)
  }


  def IsMemberOfTodo(todoId: Int)(f: => (User, JdbcBackend.Session) => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    user.id.map { userId =>
      DB.withSession { implicit s =>
        if(UserProjectRelDAO.isMemberTodo(todoId, userId) ) {
          f(user, s)(request)
        } else {
          Results.Forbidden
        }
      }
    }.getOrElse(Results.Forbidden)
  }

}
