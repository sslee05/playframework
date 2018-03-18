package common

import play.api.mvc.ActionBuilderImpl
import play.api.mvc.BodyParsers
import javax.inject.{Inject,Singleton}
import scala.concurrent.ExecutionContext
import play.api.mvc.Action
import play.api.mvc.Request
import scala.concurrent.Future
import play.api.mvc.Result
import play.api.Logger
import play.api.mvc.BodyParser
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import play.api.mvc.DefaultActionBuilder
import play.api.mvc.PlayBodyParsers
import play.api.http.FileMimeTypes
import play.api.i18n.MessagesApi
import play.api.i18n.Langs

@Singleton
class LoggingAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  self =>
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    println("#####Calling Logging action")
    block(request)
  }
  
  /*
  override def composeAction[A](action: Action[A]): Action[A] = {
    println("#####Calling Logging action1")
    action
  }
  */
  
  
}




/*
case class TestControllerComponents @Inject() (
    actionBuilder: LoggingAction,
    parsers: PlayBodyParsers,
    messagesApi: MessagesApi,
    langs: Langs,
    fileMimeTypes: FileMimeTypes,
    executionContext: scala.concurrent.ExecutionContext)
  extends ControllerComponents
  
class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser){
  
//  def apply[A](action: Action[A]): Action[A] = {
//    println("###############################Calling action")
//    action
//  }
//  
//  def logging[A](action: Action[A])= Action.async(action.parser) { request =>
//    println("Calling action")
//    action(request)
//  }
  
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    println("###############################invokeBlock call##########")
    block(request)
  }
//  override def composeAction[A](action: Action[A]) = {
//    println("###############################compose call##########")
//    action
//  }
  
}
*/