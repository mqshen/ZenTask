package models

/**
 * Created by GoldRatio on 5/21/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import util.DateHelper
import com.fasterxml.jackson.annotation.JsonIgnore

case class Todo ( id: Option[Int],
                      description: String,
                      listId: Int,
                      ownId: Int,
                      workerId: Option[Int],
                      deadline: Option[Date],
                      done: Boolean,
                      createTime: Timestamp,
                      modifyTime: Option[Timestamp],
                      projectId: Int,
                      teamId: Int,
                      deleted: Boolean = false
                      ) {

  def deadlineDisplay() = {
    deadline.map{ deadline =>
      DateHelper.formDate(deadline)
    }.getOrElse("没有截止时间")
  }

  lazy val worker = {
    workerId.map { workerId =>
      DB.withSession{ implicit s =>
        UserDAO.findById(workerId).map(user => user.name)
      }
    }.getOrElse("未指派")
  }

  @JsonIgnore
  lazy val list = {
    DB.withSession{ implicit s =>
      TodolistDAO.findById(listId).get
    }
  }

  @JsonIgnore
  lazy val comments = {
    DB.withSession{ implicit s =>
      CommentDAO.findByCommentableTypeAndId("todo", id.get)
    }
  }
}

class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description")
  def listId = column[Int]("list_id")
  def ownId = column[Int]("own_id")
  def workerId = column[Option[Int]]("worker_id")
  def deadline = column[Option[Date]]("deadline")
  def done = column[Boolean]("done")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def projectId = column[Int]("project_id")
  def teamId = column[Int]("team_id")
  def deleted = column[Boolean]("deleted")

  def * = (id, description, listId, ownId, workerId, deadline, done, createTime, modifyTime, projectId, teamId, deleted) <> (Todo.tupled, Todo.unapply _)
}

object TodoDAO {

  val todos = TableQuery[TodoTable]

  def create(Todo: Todo)(implicit s: Session) {
    todos.insert(Todo)
  }

  def create(listId: Int, description: String, ownId: Int, workerId: Option[Int], deadline: Option[Date], projectId: Int, teamId: Int)(implicit s: Session): Todo = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val todo = new Todo(None, description, listId, ownId, workerId, deadline, false, currentTime, None, projectId, teamId)
    val id = (todos returning todos.map(_.id)) += (todo)
    todo.copy(id = id)
  }

  def findById(id: Int)(implicit s: Session): Option[Todo] =
    todos.where(_.id === id).firstOption

  def findByListId(listId: Int)(implicit s: Session): List[Todo] = {
    todos.where(_.listId === listId).list()
  }

  def findUndoneByListId(listId: Int)(implicit s: Session): List[Todo] = {
    todos.where(_.listId === listId).where(_.done === false).where(_.deleted === false).list()
  }

  def update(id: Int, description: Option[String], workerId: Option[Int], deadline: Option[Date])(implicit s: Session): Option[Todo] = {
    description.map{description=>
      val q = for { t <- todos if t.id === id} yield (t.description, t.workerId, t.deadline)
      q.update(description, workerId, deadline)
    }.getOrElse {
      val q = for { t <- todos if t.id === id} yield (t.workerId, t.deadline)
      q.update(workerId, deadline)
    }
    findById(id)
  }

  def update(id: Int, flag: Boolean, workerId: Option[Int])(implicit s: Session): Option[Todo] = {
    if(flag) {
      val q = for { t <- todos if t.id === id} yield (t.done, t.workerId, t.deadline)
      val currentTime = Some(new Date(System.currentTimeMillis()))
      q.update(flag, workerId, currentTime)
    }
    else {
      val q = for { t <- todos if t.id === id} yield (t.done)
      q.update(flag)
    }
    findById(id)
  }

  def delete(id: Int)(implicit s: Session) {
    val q = for { t <- todos if t.id === id} yield (t.deleted)
    q.update(true)
  }


  def findUncompletedByTeamIdAndWorkerId(teamId: Int, workerId: Int)(implicit s: Session): List[Todo] = {
    todos.where(_.teamId === teamId).where( _.workerId === workerId ).where(_.done === false).list
  }

}
