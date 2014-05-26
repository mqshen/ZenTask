
import java.util.Locale
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent.Future

/**
 * Created by GoldRatio on 5/14/14.
 */

object Global extends GlobalSettings {

  override def onStart(application: Application) {
    Locale.setDefault(Locale.CHINESE)
  }


//  override def onError(request: RequestHeader, ex: Throwable) = {
//    Future.successful(InternalServerError(
//      views.html.errorPage(ex)
//    ))
//  }
//
//
//  override def onHandlerNotFound(request: RequestHeader) = {
//    Future.successful(NotFound(
//      views.html.notFoundPage(request.path)
//    ))
//  }
//
//  override def onBadRequest(request: RequestHeader, error: String) = {
//    Future.successful(BadRequest("Bad Request: " + error))
//  }

}
