package controllers

import com.fasterxml.jackson.databind.{SerializerProvider, JsonSerializer, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import play.api.mvc._
import play.api.http.Writeable
import play.api.mvc.Results.Status
import play.api.http.Status._
import play.api.libs.iteratee.Enumerator
import play.api.templates.HtmlFormat
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.SimpleResult
import com.fasterxml.jackson.core.JsonGenerator

/**
 * Created by GoldRatio on 5/19/14.
 */
class  NullSerializer extends JsonSerializer[AnyRef] {
  override def serialize(value: AnyRef, jgen: JsonGenerator, provider: SerializerProvider)  {
    jgen.writeString("")
  }
}

object RepresentResult {
  val objectMapper = {
    val obj = new ObjectMapper
    obj.registerModule(DefaultScalaModule)
    obj.getSerializerProvider.setNullValueSerializer(new NullSerializer)
    obj
  }

  def representationNotFound()(implicit request: Request[_], writeable: Writeable[String]): SimpleResult = {
    if (request.accepts("text/html")) {
      (new Status(NOT_FOUND))("")
    }
    else if (request.accepts("application/json")) {
      (new Status(OK))("resources not found")
    }
    else {
      (new Status(BAD_REQUEST))("")
    }
  }



  def representationOk[C, T](content: C, jsonContent: T)(implicit request: Request[_], writeable: Writeable[C]): SimpleResult = {
    if (request.accepts("text/html")) {
      (new Status(OK))(content)
    }
    else if(request.accepts("text/javascript")) {
      (new Status(OK))(content).as("text/javascript")
    }
    else if (request.accepts("application/json")) {
      import scala.concurrent.ExecutionContext.Implicits.global
      val result = Map("returnCode" -> 0,
        "content" -> jsonContent)
      val enumerator = Enumerator.outputStream { os =>
        objectMapper.writeValue(os, result)
      }
      (new Status(OK).chunked(enumerator >>> Enumerator.eof)).as("application/json")
    }
    else {
      (new Status(BAD_REQUEST))("")
    }
  }
}



