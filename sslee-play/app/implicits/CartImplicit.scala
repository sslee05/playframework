package implicits

import com.google.inject.ImplementedBy
import models.templatechapter.CartDao
import scala.concurrent.Future
import models.templatechapter.Cart
import models.slickchapter.Product

//@ImplementedBy(classOf[CartDao])
trait CartImplicit { 
  
  //override implicit def cart: Cart = super.cart
  //implicit def cart: Cart = self.cart
}

