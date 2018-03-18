package common

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import javax.inject.{Inject,Singleton}
import play.api.mvc.ActionBuilder
import play.api.mvc.ActionTransformer
import play.api.mvc.AnyContent
import play.api.mvc.BodyParsers
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.WrappedRequest
import play.api.mvc.Action


//case class UserRequest[A](val userName: String, val request: Request[A]) extends WrappedRequest[A](request)
case class UserRequest[A](val userName: Option[String], val request: Request[A]) extends WrappedRequest[A](request)


@Singleton
class AuthAction @Inject() (val parser: BodyParsers.Default,val ex: ExecutionContext)
  extends ActionBuilder[Request,AnyContent] { //with ActionTransformer[Request,UserRequest] { self =>
  
  
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    println("###############call AuthAction invokeBlock")
    block(request)
  }
  
  def executionContext: ExecutionContext = ex
  
  def transform[A](request: Request[A]) = Future.successful {
    println("###############call AuthAction transform")
    UserRequest(Some("sslee"),request)
  }
   
}

