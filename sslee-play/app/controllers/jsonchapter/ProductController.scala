package controllers.jsonchapter

import models.jsonchapter.Product08Dao
import models.jsonchapter.Product08
import models.jsonchapter.JsonParser._
import play.api.mvc.ControllerComponents
import javax.inject.{Inject,Singleton}
import play.api.mvc.AbstractController
import play.api.i18n.I18nSupport
import scala.concurrent.ExecutionContext
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.Logger


@Singleton
class ProductController @Inject()(cc: ControllerComponents, productDao: Product08Dao)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {
  
  
  def index = Action { implicit request =>
    
    val category = JsString("paperclips")
    val quantity  = JsNumber(42)
    val jsobj:JsObject = Json.obj("cate" -> category, "qt" -> quantity)
    val jslist:JsArray = Json.arr(category,quantity)
    
    //Option 를 JSON으로 직렬화시 
    //일반적으로 Option이 None 일때 Null 로 직렬화 하는 것이 일반적 이다.
    val op = Some("a")
    op.map(JsString(_)).getOrElse(JsNull) 
    
    Logger.debug(Json.stringify(jsobj))
    
    Ok(views.html.jsonchapter.jsonindex())
  }
  
  def list = Action.async { implicit request => 
    productDao.findAll.map(rs => Ok(Json toJson (rs.map(pd => pd.ean) )))
  }
  
  def details(ean: Long) = Action.async { implicit request =>
    Logger.debug("####### detail call:"+ ean)
    productDao.findByEan(ean).map(productOp => productOp.map(product => Ok(Json toJson product)) getOrElse NotFound)
  }
  
  def save(ean: Long) = Action(parse.json).async { implicit request =>
    val productJson = request.body
    val product = productJson.as[Product08]
    Logger.debug("######### update data emptyString"+product.description+":")
    productDao.updateAndResult(product).map(products => Ok(Json toJson products))
  }
    
}