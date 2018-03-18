object chapter02 {
  
  import slick.jdbc.H2Profile.api._
  
  
  case class Product(id: Long, ean: Long, name: String, description: String)
  
  class ProductTable(tag: Tag) extends Table[Product](tag,"products")  {
	  def id = column[Long]("id",O.PrimaryKey, O.AutoInc)
	  def ean = column[Long]("ean")
	  def name = column[String]("name")
	  def description = column[String]("description")
	  def * = (id,ean,name,description).mapTo[Product]
	};import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(485); 
	
	val products = TableQuery[Product];System.out.println("""products  : <error> = """ + $show(products ))}
}
