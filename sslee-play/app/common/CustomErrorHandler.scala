package common

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import javax.inject.Singleton
import play.api.Logger

@Singleton
class CustomErrorHandler extends HttpErrorHandler {
  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    
    Logger.debug("############ client error called")
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Logger.debug("############ Server error called")
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }
}