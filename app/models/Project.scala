package models

/**
 * Created by GoldRatio on 5/19/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import com.fasterxml.jackson.annotation.JsonIgnore

case class Project ( id: Option[Int],
                  name: String,
                  description: String,
                  createTime: Timestamp,
                  modifyTime: Option[Timestamp],
                  teamId: Int,
                  color: String = "ffffff"
                  ) extends Color {
  @JsonIgnore
  lazy val todolists = {
    DB.withSession{ implicit s =>
      id.map{ id =>
        TodolistDAO.findByProjectId(id)
      }.getOrElse(List())
    }
  }

  @JsonIgnore
  lazy val recentMessages = {
    DB.withSession{ implicit s =>
      id.map{ id =>
        MessageDAO.findByProjectId(id, 5)
      }.getOrElse(List())
    }
  }
}

class ProjectTable(tag: Tag) extends Table[Project](tag, "project") {
  def id = column[Option[Int]]("id", O.PrimaryKey)
  def name = column[String]("name")
  def description = column[String]("description")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def teamId = column[Int]("team_id")
  def color = column[String]("color")

  def * = (id, name, description, createTime, modifyTime, teamId, color) <> (Project.tupled, Project.unapply _)
}

object ProjectDAO {

  val Projects = TableQuery[ProjectTable]

  def create(Project: Project)(implicit s: Session) {
    Projects.insert(Project)
  }


  def findById(id: Int)(implicit s: Session): Option[Project] =
    Projects.where(_.id === id).firstOption

  def findByTeamId(teamId: Int)(implicit s: Session): List[Project] = {
    Projects.where(_.teamId === teamId).list()
  }

}

