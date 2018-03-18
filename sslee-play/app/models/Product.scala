package models

case class Product(ean: Long, name: String, description: String)

object Product {
  var products = Set( 
      Product(5010255079763L, "Paperclips Large1", "Large Plain Pack of 1000"),
      Product(5018206244666L, "Paperclips Large2", "Large Plain Pack of 2000"),
      Product(5018306332812L, "Paperclips Large3", "Large Plain Pack of 3000"),
      Product(5018306312913L, "Paperclips Large4", "Large Plain Pack of 4000"),
      Product(5018206244611L, "Paperclips Large5", "Large Plain Pack of 5000")
      )
  
  //목록 조회 
  def findAll = products.toList.sortBy(_.ean)
  
  //상세조회 
  def findByEan(ean: Long): Option[Product] = products.find(_.ean == ean)
  
  //등록 
  def add(product: Product) = {
    products = products + product
  }
  
}