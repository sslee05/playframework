package models.templatechapter

import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._

import implicits.CartImplicit
import javax.inject.Inject
import javax.inject.Singleton
import models.slickchapter.Product
import models.slickchapter.ProductDao
import com.google.inject.ImplementedBy
import play.api.mvc.AbstractController

case class Cart(products: Map[Int,Product]) {
  def productCount: Int = products.keys.fold(1)(_+_)
}

@Singleton
class CartDao @Inject() (productDao: ProductDao)(implicit ec: ExecutionContext) {
  
  def cart: Cart = { 
    val rs = productDao.getAll
    Cart{
      Await.result(rs.map(rs => rs.zipWithIndex.map(t => (t._2 -> t._1)).toMap),2.second)
    }
  }
}

//@ImplementedBy(classOf[CartDao])
trait CartImpli {
  //type t = CartDao
  //implicit def cart : Cart = t.getCart
  
}
