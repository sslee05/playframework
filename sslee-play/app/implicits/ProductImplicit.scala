package implicits

import play.api.libs.json.Writes
import play.api.libs.json.Json
import models.slickchapter.Product

object ProductImplicit {
  implicit val productWites = new Writes[Product] {
    def writes(product: Product) = Json.obj (
      "id" -> product.id,
      "ean" -> product.ean,
      "name" -> product.name,
      "description" -> product.description
    )
  }
}