package models

/**
 * Created by GoldRatio on 5/23/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._

case class Team ( id: Option[Int],
                  name: String,
                  description: String,
                  createTime: Timestamp,
                  modifyTime: Option[Timestamp]
                  ) {

}

class TeamTable(tag: Tag) extends Table[Team](tag, "team") {
  def id = column[Option[Int]]("id", O.PrimaryKey)
  def name = column[String]("name")
  def description = column[String]("description")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")

  def * = (id, name, description, createTime, modifyTime) <> (Team.tupled, Team.unapply _)
}

object TeamDAO {

  val teams = TableQuery[TeamTable]

  def create(team: Team)(implicit s: Session) {
    teams.insert(team)
  }


  def findTeamsByUserId(userId: Int)(implicit s: Session): List[Team] = {
    (for {
      (team, user) <- teams innerJoin UserTeamRelDAO.userTeamRels on { case (t1, t2) =>
        t1.id === t2.teamId
      }
      if user.userId === userId
    } yield team).list
  }
}
