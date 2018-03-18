package models.formchapter

case class Product (
    ean: Long, 
    name: String,
    description: String,
    pieces: Int,
    active: Boolean,
    colors : List[String]
)

object ProductDao {
  val rs = List(
   Product(12345,"sslee01","a",1,true,List("blue","red")),
   Product(22345,"sslee02","b",2,true,List("blue","red")),
   Product(32345,"sslee03","c",3,true,List("blue","red")),
   Product(42345,"sslee04","d",4,true,List("blue","red")),
   Product(52345,"sslee05","e",5,true,List("blue","red")),
  )
  
  def findById(ean: Long): Option[Product] = {
    rs.find(p => p.ean == ean)
  }
}