package controllers.authentication

import play.api.mvc.AbstractController
import play.api.i18n.I18nSupport
import javax.inject.{Inject,Singleton}
import scala.concurrent.ExecutionContext
import play.api.mvc.ControllerComponents
import play.api.mvc.Request
import play.api.mvc.AnyContent
import play.api.mvc.Action
import play.api.mvc.Result
import play.api.mvc.Headers
import play.api.mvc._
import play.mvc.Http
import play.api.Logger
import play.api.http.HeaderNames


@Singleton
class SampleAuthenticationController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) 
  extends AbstractController(cc) with I18nSupport {
  
  def index = AuthenticationAction03 { request =>
    Ok("authenticated ....")
  }
  
  def authenticate(request: Request[AnyContent]) = false
  
  def AuthenticatedAction(f: Request[AnyContent] => Result): Action[AnyContent] = Action { implicit request =>
    if(authenticate(request)) f(request)
    else Unauthorized
  }
  
  def authenticate2(userId: String, password: String) = true
  
  def AuthenticatedAction02(f: Request[AnyContent] => Result): Action[AnyContent] = Action { implicit request =>
    readQueryString(request) map { either => either match {
        case Right(credi) => if(authenticate2(credi._1, credi._2)) f(request) else Unauthorized("Invalid user name or password") 
        case Left(error) =>  error
      }
    } getOrElse Unauthorized("No user name and password provided")
  }
  
  def readQueryString(request: Request[_]): Option[Either[Result,(String,String)]] = {
    request.queryString.get("user").map { user =>
      request.getQueryString("password").map { password =>
        Right((user.head,password))
      } getOrElse Left(BadRequest("password not specified"))
    }
  }
  
  def AuthenticationAction03(f:Request[_] => Result): Action[AnyContent] = Action { implicit request =>
    readBasicAuthentication(request.headers) map { either => 
      either match {
        case Right(cred) => if(authenticate2(cred._1,cred._2)) f(request) else Unauthorized("Invalid user name or password")
        case Left(error) => error
      }
    } getOrElse {
      val authenticate = (HeaderNames.WWW_AUTHENTICATE,"Basic")
      Unauthorized("No user name and password provided").withHeaders(authenticate)
    }
  }
  
  def readBasicAuthentication(headers: Headers): Option[Either[Result,(String,String)]] = {
    headers.get(Http.HeaderNames.AUTHORIZATION).map { headerValue => 
      Logger.debug("################## headerValue:"+headerValue)//Basic amFjazoxMjM0NQ==
      val BasicHeader = "Basic (.*)".r //정규식 표현 
      headerValue match {
        case BasicHeader(base64) => {
          try {
            import org.apache.commons.codec.binary.Base64
            val decodedBytes = Base64.decodeBase64(base64.getBytes)
            val credentials = new String(decodedBytes).split(":",2)
            Logger.debug("############ decodevalue:"+credentials(0)+":"+credentials(1))
            if(credentials.length != 2) Left(BadRequest("Invalid basic authentication"))
            else Right((credentials(0),credentials(1)))
          }
        }
        case _ => Left(BadRequest("invalid Authorization header"))
      }
    }
  }
}