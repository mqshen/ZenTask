package util

import models.{UserDAO, User}

import play.api.Play.current
import play.api.db.slick._

/**
 * Created by GoldRatio on 5/22/14.
 */
object UserHelper {

  def findUsersByProjectId(projectId: Int): List[User] = {
    DB.withSession{ implicit s =>
      UserDAO.findUsersByProjectId(projectId)
    }
  }

  val fileServer = current.configuration.getString("fileServer")

}
