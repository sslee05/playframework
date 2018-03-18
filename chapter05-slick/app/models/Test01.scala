package models

object Test01 extends App {
  import slick.jdbc.H2Profile.api._
  
  class ProductTable(tag: Tag) extends Table[Product](tag,"products")  {
  
  def id = column[Long]("id",O.PrimaryKey, O.AutoInc)
  def ean = column[Long]("ean")
  def name = column[String]("name")
  def description = column[String]("description")
  def * = (id,ean,name,description).mapTo[Product]
}

  val products: slick.lifted.TableQuery[ProductTable] = TableQuery[ProductTable]
  val filteredProducts = products.filter(_.name === "CAR")
  println(filteredProducts.result.statements.mkString)
  println(products.schema.createStatements.mkString)
  

}