package controllers

import play.api.mvc.AbstractController
import models.ProductDao
import javax.inject.{Inject,Singleton}
import play.api.mvc.ControllerComponents
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import models.Product
import play.api.libs.json.Json

@Singleton
class ProductController @Inject() (cc: ControllerComponents, productDao: ProductDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  
  def findByName(name: String) = Action.async { implicit request =>
    
    val datas = productDao.findByName(name)
    datas.map(rs => Ok(views.html.products(rs)))
  }
  
  def addDatas(id: Long,ean: Long, name: String, description: String) = Action.async { implicit request =>
    
    val rx = List(Product(id,ean,name,description),Product(id,ean,name,description))
    val result = productDao.adds(rx)
    
    result.map(cnt => Ok(Json toJson cnt))
  }
  
  def execute(id: Long,ean: Long, name: String, description: String) = Action.async { implicit request =>
    val rx = List(Product(id,ean,name,description),Product(id,ean,name,description))
    val result = productDao.execute(rx)
    result.map(cnt => Ok(Json toJson cnt))
  }
  
}