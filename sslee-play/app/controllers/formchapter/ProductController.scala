package controllers.formchapter

import play.api.mvc.ControllerComponents
import play.api.mvc.AbstractController
import play.api.i18n.I18nSupport
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import play.api.data.Form
import play.api.data.Forms._
import models.formchapter.Product
import models.formchapter.ProductDao
import play.api.Logger
import play.api.libs.json.Json
import models.formchapter.Group
import models.formchapter.User
import play.api.data.format.Formatter
import org.joda.time.LocalDate
import play.api.data.FormError
import scala.util.Try

//import play.api.i18n._

class ProductController @Inject()(cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {
  
  
  val productForm = Form (
    mapping(
      "ean" -> longNumber.verifying(ProductDao.findById(_) == None),
      "name" -> nonEmptyText,
      "description" -> text,
      "pieces" -> number,
      "active" -> boolean,
      "colors" -> list(text)
    )(Product.apply)(Product.unapply).verifying{ product => 
      !product.active && product.description.nonEmpty
    }    
  )
  
  
  import models.formchapter.Implicit._
  //val localDateMapping = play.api.data.Forms.of(localDateFormat)
  
  val userForm = Form(
     mapping(
       "userName" -> nonEmptyText(8),
       "realName" -> optional(text),
       "email" -> email,
       "groups" -> list(mapping(
         "groupName" -> nonEmptyText,
         "createDate" -> of[LocalDate] //Forms.of(implicit binder: Formatter[T]) 이다.
       )(Group.apply)(Group.unapply))
     )(User.apply)(User.unapply)
  )
  
  def createForm() = Action { implicit request =>
    implicit val lang = request.lang(messagesApi)
    Ok(views.html.formchapter.productform())
  }
  
  def create() = Action { implicit request =>
    Logger.debug("######### create method")
    productForm.bindFromRequest()(request).fold(
      formWithError => {
        Logger.debug("fail =>")
        BadRequest
      },
      product => {
        Logger.debug("success=>")
        Ok(Json toJson "OK")
      }
    )
  }
  
  def createForm02() = Action { implicit request =>
    val msg = messagesApi("products.form")(request.lang)
    Logger.debug("########"+msg)
    Ok(views.html.formchapter.productform2(productForm))
  }
  
  def create02() = Action { implicit request =>
    implicit val lang = request.lang(messagesApi)
    productForm.bindFromRequest()(request).fold(
      formWithError => {
        Logger.debug("#######error"+ formWithError.errors)
        Ok(views.html.formchapter.productform2(formWithError))
      },
      product => Ok(Json toJson "Ok")
    )
  }
  
  def createUserForm() = Action { implicit request =>
    Ok(views.html.formchapter.userform(userForm))
  }
  
  def createUser = Action { implicit request =>
    userForm.bindFromRequest().fold( {
      Logger.debug("##### userCreate called faile")
      formWithError => Ok(views.html.formchapter.userform(formWithError))
    },
    {
      Logger.debug("##### userCreate called success")
      user => Ok(Json toJson "Ok")
    }
    )
  }
  
}