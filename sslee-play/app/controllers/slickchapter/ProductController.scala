package controllers.slickchapter

import javax.inject.Inject
import models.slickchapter.ProductDao
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import play.api.libs.json.Json

import implicits.ProductImplicit._
import scala.concurrent.ExecutionContext
import models.slickchapter.Product


class ProductController @Inject() (cc: ControllerComponents,productDao: ProductDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  
  def findById(id: Long) = Action.async { implicit request =>
    val resultFuture = productDao findById id
    resultFuture.map {
      case Some(a) => Ok(Json toJson a) 
      case None => Ok(Json toJson "Empty Data")  
    }
  }
  
  def findByName(name: String) = Action.async {
    val resultFuture = productDao findByName name
    resultFuture.map(ma => Ok(Json toJson ma))
  }
  
  def addProducts(ean: Long, name: String, description: String) = Action.async { implicit request =>
    val resultFuture = productDao addProducts List(Product(1L,ean,name,description),Product(1L,ean,name,description))
    resultFuture map (ma => Ok(Json toJson ma.get))
  }
  
  def executeProducts(ean: Long, name: String, description: String) = Action.async { implicit request =>
    val resultFuture = productDao executeProducts List(Product(1L,ean,name,description),Product(1L,ean,name,description))
    resultFuture map (cnt => Ok(Json toJson cnt))
  }
  
  def executeFail = Action.async {
    val resultFuture = productDao executeRollback List(Product(1L,2l,"a","a"),Product(1L,3L,"b","b"))
    resultFuture map (cnt => Ok(Json toJson cnt))
  }
  
  def deleteProduct(id: Long) = Action.async { implicit request =>
    val resultFuture = productDao deleteProduct id
    resultFuture map (cnt => Ok(Json toJson cnt))
  }
  
}