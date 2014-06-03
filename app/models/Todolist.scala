package models

/**
 * Created by GoldRatio on 5/21/14.
 */

import java.sql.{Timestamp, Date}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.DB
import scala.slick.jdbc.StaticQuery.interpolation

case class Todolist ( id: Option[Int],
                     name: String,
                     description: Option[String],
                     createTime: Timestamp,
                     modifyTime: Option[Timestamp],
                     projectId: Int,
                     uncompleteNo: Int = 0
                     ) {
  lazy val todos = {
    DB.withSession{ implicit s =>
      id.map{ id =>
        TodoDAO.findUndoneByListId(id)
      }.getOrElse(List())
    }
  }
}

class TodolistTable(tag: Tag) extends Table[Todolist](tag, "todolist") {
  def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def createTime = column[Timestamp]("create_time")
  def modifyTime = column[Option[Timestamp]]("modify_time")
  def projectId = column[Int]("project_id")
  def uncompleteNo = column[Int]("uncomplete_no")

  def * = (id, name, description, createTime, modifyTime, projectId, uncompleteNo) <> (Todolist.tupled, Todolist.unapply _)
}

object TodolistDAO {

  val todolists = TableQuery[TodolistTable]

  def create(todolist: Todolist)(implicit s: Session) {
    todolists.insert(todolist)
  }

  def create(projectId: Int, name: String)(implicit s: Session): Todolist = {
    val currentTime = new Timestamp(System.currentTimeMillis())
    val todolist = new Todolist(None, name, None, currentTime, None, projectId)
    val id = (todolists returning todolists.map(_.id)) += (todolist)
    todolist.copy(id = id)
  }

  def doModify(id: Int, name: String, description: Option[String])(implicit s: Session): Option[Todolist] = {
    val currentTime = Some(new Timestamp(System.currentTimeMillis()))
    val q = for { t <- todolists if t.id === id} yield (t.name, t.description, t.modifyTime)
    q.update(name, description, currentTime)
    findById(id)
  }

  def findById(id: Int)(implicit s: Session): Option[Todolist] =
    todolists.where(_.id === id).firstOption

  def findByProjectId(projectId: Int)(implicit s: Session): List[Todolist] = {
    todolists.where(_.projectId === projectId).where(_.uncompleteNo > 0).list()
  }

  def completeTodo(id: Int)(implicit s: Session) {
    sqlu"update todolist set uncomplete_no = uncomplete_no - 1 where id = $id".first()
  }

  def uncompleteTodo(id: Int)(implicit s: Session) {
    sqlu"update todolist set uncomplete_no = uncomplete_no + 1 where id = $id".first()
  }
}

