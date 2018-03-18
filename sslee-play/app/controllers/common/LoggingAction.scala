package controllers.common

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import javax.inject.{Inject,Singleton}
import play.api.Logger
import play.api.mvc.Action
import play.api.mvc.ActionBuilderImpl
import play.api.mvc.BodyParsers
import play.api.mvc.Request
import play.api.mvc.Result

@Singleton
class LoggingAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request)
  }
  override def composeAction[A](action: Action[A]) = {
    Logger.debug("########LLLLLLLOOOOOOOGGGGGGGIIIIIINNNNNNGGG")
    println("########LLLLLLLOOOOOOOGGGGGGGIIIIIINNNNNNGGG")
    action 
  }
}