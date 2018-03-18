package utils

object JsonConvertTest extends App {
  
  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import play.api.libs.json.JsPath
  
  case class MockData(name: Option[String],age: Int)
  
  implicit val mockReades:Reads[MockData] = (
      (JsPath \ "name").readNullable[String] and
      (JsPath \ "age").read[Int]
  )(MockData.apply _) 
  
  val emptyData:JsValue = Json.parse("""{"name": "","age":0}""")
  println(":"+emptyData.as[MockData]+":")
}