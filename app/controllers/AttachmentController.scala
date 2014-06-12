package controllers

import play.api._
import play.api.mvc._
import util.{StorageClient, TrackerClient}
import play.api.http.Status._
import scala.Some
import play.api.libs.iteratee.Enumerator

/**
 * Created by GoldRatio on 6/12/14.
 */
object AttachmentController extends Controller {

  def upload = Action(parse.temporaryFile) { request =>
    val extName = request.headers.get("X_FILENAME") match {
      case Some(fileName) =>
        val nPos = fileName.lastIndexOf('.')
        fileName.substring(nPos+1)
      case _ =>
        "jpg"
    }
    val picture = request.body
    val tracker = new TrackerClient()
    val trackerServer = tracker.getConnection()
    val  client = new StorageClient(trackerServer, null)
    val fileDescription = client.upload_file(picture.file, extName)
    val path = fileDescription._1 + "/" + fileDescription._2

    if (request.accepts("text/html")) {
      Ok(views.html.file(path))
    }
    else if (request.accepts("application/json")) {
      import scala.concurrent.ExecutionContext.Implicits.global
      val result = "{\"returnCode\" : 0,\"content\" :\"" + path + "\" }"
      val enumerator = Enumerator.outputStream { os =>
        os.write(result.getBytes())
        os.close()
      }
      (new Status(OK).chunked(enumerator >>> Enumerator.eof)).as("application/json")
    }
    else {
      BadRequest
    }
  }

}