package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.db.slick._
import models.{UserDAO, TodolistDAO, TodoDAO, ProjectDAO}
import play.api.data.Form
import play.api.data.Forms._
import java.security.MessageDigest
import org.apache.commons.codec.binary.Base64
import java.util.UUID
import play.api.cache.Cache

/**
 * Created by GoldRatio on 5/23/14.
 */
object UserController extends Controller {

  val loginForm = Form(
    tuple(
      "email" -> email,
      "password" -> text
    )
  )

  def md5(s: String): String = {
    val encode = MessageDigest.getInstance("MD5").digest(s.getBytes)
    new String(Base64.encodeBase64(encode))
  }

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def doLogin = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.login(formWithErrors)),
      user => {
        DB.withSession{ implicit session =>
          val password = md5(user._1 + user._2)
          UserDAO.auth(user._1, password) match {
            case Some(user) =>
              val sessionId = UUID.randomUUID().toString
              Cache.set(sessionId, user)
              Results.Redirect(routes.TeamController.index).withSession("sessionId" -> sessionId)
            case _ =>
              Ok(views.html.login(loginForm))
          }
        }
      }
    )
  }

}
