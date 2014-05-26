package models


import play.api.db.slick.Config.driver.simple._

/**
 * Created by GoldRatio on 5/22/14.
 */
case class UserTeamRel (
                            userId: Int,
                            projectId: Int
                            ) {

}

class UserTeamRelTable(tag: Tag) extends Table[UserTeamRel](tag, "user_team_rel") {
  def userId = column[Int]("user_id")
  def teamId = column[Int]("team_id")

  def * = (userId, teamId) <> (UserTeamRel.tupled, UserTeamRel.unapply _)
}

object UserTeamRelDAO {

  val userTeamRels = TableQuery[UserTeamRelTable]

  def isMember(teamId: Int, userId: Int)(implicit s: Session) = {
    val query = for(row <- userTeamRels if row.teamId === teamId && row.userId === userId) yield row
    query.length.run == 1
  }

}