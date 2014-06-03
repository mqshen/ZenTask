package models

/**
 * Created by GoldRatio on 2/19/14.
 */

import java.sql.Date
import play.api.db.slick.Config.driver.simple._
import com.fasterxml.jackson.annotation.JsonIgnore

case class User (
  id: Option[Int],
  email: String,
  name: String,
  password: String
) {

}

class UserTable(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Option[Int]]("id", O.PrimaryKey)
  def email = column[String]("email")
  def name = column[String]("name")
  @JsonIgnore
  def password = column[String]("password")

  def * = (id, email, name, password) <> (User.tupled, User.unapply _)
}

object UserDAO {

  val users = TableQuery[UserTable]

  def create(user: User)(implicit s: Session) {
    users.insert(user)
  }

  def findByEmail(mail: String)(implicit s: Session): Option[User] =
    users.where(_.email === mail).firstOption

  def auth(mail: String, password: String)(implicit s: Session): Option[User] =
    users.where(_.email === mail).where(_.password === password).firstOption

  def findById(id: Int)(implicit s: Session): Option[User] = {
    users.where(_.id === id).firstOption
  }

  def findUsersByProjectId(projectId: Int)(implicit s: Session): List[User] = {
    (for {
      (user, project) <- users innerJoin UserProjectRelDAO.userProjectRels on { case (t1, t2) =>
        t1.id === t2.userId
      }
      if project.projectId === projectId
    } yield user).list
  }
}

