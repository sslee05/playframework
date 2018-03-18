package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.Configuration
import play.api.mvc.AbstractController
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import play.api.mvc.Request
import play.api.libs.json.Json
import implicits.CartImplicit
import play.api.i18n.MessagesProvider
import play.api.i18n.I18nSupport
import play.api.i18n.Messages
import play.api.i18n.Lang

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request =>
    //val messages2: Messages = messagesApi.preferred(request)
    //implicit val lang = messages2.lang
    implicit val lang = request.lang(messagesApi)
    
    Ok(views.html.index("Hellow world playframework"))
  }
}
