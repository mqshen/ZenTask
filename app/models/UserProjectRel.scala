package models

import play.api.db.slick.Config.driver.simple._

/**
 * Created by GoldRatio on 5/22/14.
 */
case class UserProjectRel (
                  userId: Int,
                  projectId: Int
                  ) {

}

class UserProjectRelTable(tag: Tag) extends Table[UserProjectRel](tag, "user_project_rel") {
  def userId = column[Int]("user_id")
  def projectId = column[Int]("project_id")

  def * = (userId, projectId) <> (UserProjectRel.tupled, UserProjectRel.unapply _)
}

object UserProjectRelDAO {

  val userProjectRels = TableQuery[UserProjectRelTable]

  def isMember(projectId: Int, userId: Int)(implicit s: Session): Option[Project] = {
    val query = for((rel, project) <- userProjectRels innerJoin(ProjectDAO.Projects) on { case (t1, t2) =>
      t1.projectId === t2.id
    } if rel.projectId === projectId && rel.userId === userId) yield project
    query.firstOption
  }

  def isMemberTodo(todoId: Int, userId: Int)(implicit s: Session) = {
    val query = for{
      (project, todo) <- userProjectRels innerJoin TodoDAO.todos on {case (t1, t2) =>
        t1.projectId === t2.projectId
    } if todo.id === todoId && project.userId === userId} yield todo
    query.length.run == 1
  }
}
