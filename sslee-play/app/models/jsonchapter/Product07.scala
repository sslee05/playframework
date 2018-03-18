package models.jsonchapter


import slick.lifted.Tag
import slick.jdbc.DB2Profile.api._
import play.api.db.slick.DatabaseConfigProvider
import javax.inject.{Inject,Singleton}
import slick.jdbc.JdbcProfile
import scala.concurrent.Future
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.OFormat
import play.api.libs.json.Json
import play.api.data.validation.ValidationError

case class Product08 (ean: Long, name: String, description: String)

class Product08Table(tag: Tag) extends Table[Product08](tag,"PRODUCT07") {
  def ean = column[Long]("EAN",O.PrimaryKey)
  def name = column[String]("NAME")
  def description = column[String]("description")
  def * = (ean,name,description).mapTo[Product08]
}

object JsonParser {
  
  implicit val product08Reads: Reads[Product08] = (
    (JsPath \ "ean").read[Long] and
    (JsPath \ "name").read[String](maxLength[String](20)) and
    (JsPath \ "description").read[String]
    //(JsPath \ "description").read[String](email)
  )(Product08.apply _)
  
  // 기존에 있는 Reads 나 Writes 한개만 있으면 가능 
  //implicit val allForOneProduct08 = Json.format[Product08]
  
  
  implicit val product08Writes: Writes[Product08] = (
      (JsPath \ "ean").write[Long] and
      (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String]
  )(unlift(Product08.unapply))
  
  
  /* format  1개로 Reads,Writes 없이 가능 
  implicit val product08Format: OFormat[Product08] = (
      (JsPath \ "ean").format[Long] and
      (JsPath \ "name").format[String](maxLength[String](20)) and
      (JsPath \ "description").format[String]
  )(Product08.apply _,unlift(Product08.unapply))
  */
  
  implicit val JsPathWrites = Writes[JsPath]( p => Json.toJson(p.toString) )
  implicit val ValidationErrors = Writes[ValidationError](e => Json.toJson(e.message))
  
  implicit val jsonError = (
    (JsPath \ "path").write[JsPath] and
    (JsPath \ "errors").write[Seq[ValidationError]]
    tupled
  )
}

@Singleton
class Product08Dao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {
  val db = dbConfigProvider.get[JdbcProfile].db
  
  lazy val tableQuery = TableQuery[Product08Table]
  
  val init = {
    val mockDatas = Seq(
      Product08(5010255079763L ,"Parperclips Large", "Large Plain Pack of 1000"), 
      Product08(5018206244666L ,"Giant Paperclips", "Giant Plain 51mm 100 pack"),
      Product08(5018306332812L ,"Paperclip Giant Paperclips", "Giant Plain Pack of 1000"),
      Product08(5018306312913L ,"No Tear Paper clips", "NO Tear Extra Large Pack of 1000"),
      Product08(5018206244611L ,"Zebra Paperclips", "Zebra Length 28mm AS sorted 150 pack")
    )
    
    val action = tableQuery.schema.create andThen (tableQuery ++= mockDatas)
    db run action
  }
  
  def findAll: Future[Seq[Product08]] = {
    val action = tableQuery.sortBy(pt => pt.ean).result
    db run action
  }
  
  def findByEan(ean: Long): Future[Option[Product08]] = {
    val query = tableQuery.filter(pt => pt.ean === ean)
    val action = query.result.headOption
    db run action
  }
  
  def saveAndReult(product08: Product08): Future[Seq[Product08]] = {
    val action = (tableQuery += product08) andThen (tableQuery.sortBy(pt => pt.ean).result)
    db run action
  }
  
  def updateAndResult(product08: Product08): Future[Seq[Product08]] = {
    val action = tableQuery.filter(productTable => 
      productTable.ean === product08.ean).map(pt => 
        (pt.name,pt.description)).update((product08.name,product08.description)) andThen
        tableQuery.sortBy(pt => pt.ean).result
        
    db run action
  }
}