package controllers.templatechapter

import scala.concurrent.ExecutionContext

import common.AuthAction
import common.LoggingAction
import common.UserRequest
import javax.inject.Inject
import models.slickchapter.ProductDao
import play.api.i18n._
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import play.api.mvc.BodyParser
import common.RequestArchiver
import play.api.mvc.BodyParsers
import play.api.mvc.Request
import play.api.mvc.AnyContent


//import play.api.i18n.MessagesApi

//class ProductController @Inject()(cc: TestControllerComponents,productDao: ProductDao)
class ProductController @Inject()(loggingAction: LoggingAction,
    authAction: AuthAction,productDao: ProductDao,cc:ControllerComponents)
        (implicit ex:ExecutionContext)extends AbstractController(cc) with I18nSupport  {
  
  //val lang: Lang = langs.availables.head
  //implicit val messages: Messages = MessagesImpl(lang, messagesApi)
  /*
  val getCatagoies = loggingAction(parse.text) { request =>
    println("######3=>")
    //Ok("Got a body " + request.body.length + " bytes long")
    Ok(Json toJson "a")
  }
  */
  /*
  def getCatagoies = loggingAction { request =>
    println("######3=>")
    //Ok("Got a body " + request.body.length + " bytes long")
    Ok(Json toJson "a")
  }
  */
  /*
  def getCatagoies = loggingAction {
    
    Action.async { request =>
      
      println(cc.actionBuilder)
      println("######3=>")
    
      implicit val lang = request.lang(messagesApi)
      val resultF = productDao.getAll
      resultF.map(rs => Ok(views.html.templatechapter.content(rs)))
    
    }
  }
  */
  
  /*
  def getCatagoies = loggingAction { 
    authAction { request =>
      println("######3=>")
      Ok(Json toJson "a")
    }
  }
  */
  
  /*
  def getCatagoies = loggingAction {
    
    authAction.async {  request =>
        println(cc.actionBuilder)
        println("######3=>")
      
        implicit val lang = request.lang(messagesApi)
        val resultF = productDao.getAll
        resultF.map(rs => Ok(views.html.templatechapter.content(rs)))
    }
  }
  */
  
  /*
  def getCatagoies = loggingAction { //request =>
    
    authAction {  request =>
        println(cc.actionBuilder)
        println("######3=>")
      
        implicit val lang = request.lang(messagesApi)
        Ok(views.html.templatechapter.content(List()))
        //val resultF = productDao.getAll
        //resultF.map(rs => Ok(views.html.templatechapter.content(rs)))
    }
  }
  */
  
  /*
  def getCatagoies = (loggingAction andThen authAction) { request  => 
      println(cc.actionBuilder)
        println("######3=>")
      
        implicit val lang = request.lang(messagesApi)
        Ok(views.html.templatechapter.content(List()))
  }
  */
  
  def getCatagoies = (loggingAction andThen authAction) async {implicit request  => 
      println(cc.actionBuilder)
        println("######3=>")
      
        val resultF = productDao.getAll
        resultF.map(rs => Ok(views.html.templatechapter.content(rs)))
  }
  
  /*
  def tagItem(itemId: String, tag: String)(implicit ec: ExecutionContext) =
  (userAction andThen ItemAction(itemId) andThen PermissionCheckAction) { request =>
    request.item.addTag(tag)
    Ok("User " + request.username + " tagged " + request.item.id)
  }
  */
}