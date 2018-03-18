package controllers.formchapter

import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import play.api.i18n.I18nSupport
import play.api.data.Form
import play.api.data.Forms._
import play.api.data._
import models.formchapter.User
import models.formchapter.Group
import org.joda.time.LocalDate

class UserCotroller @Inject() (cc: ControllerComponents)(implicit val ec: ExecutionContext) 
  extends AbstractController(cc) with I18nSupport {
  
  import models.formchapter.Implicit._
  
  val userForm = Form(
    mapping(
      "userName" -> nonEmptyText(8),
      "realName" -> optional(text),
      "email" -> email,
      "groups" -> list(mapping(
          "groupName" -> text,
          "createDate" -> of[LocalDate])(Group.apply)(Group.unapply)) 
    )(User.apply)(User.unapply)
  )
  
    
  def createUser() = Action { implicit request =>
    userForm.bindFromRequest().fold (
      formWithError => BadRequest,
      user => Ok("User Ok!")
    )
  }
  
}