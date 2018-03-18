package models.slickchapter

//Slick은 데이터베이스 별 프로파일을 통해 대부분의 API를 제공 
import javax.inject.Inject
import javax.inject.Singleton
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase

//이는 필요한 다수의 implicit 와 type 이 정의 되어 있다.
import slick.jdbc.DB2Profile.api._
import slick.jdbc.JdbcProfile
import scala.concurrent.Future

//definded Product type case class
case class Product(id: Long, ean: Long, name: String, description: String)

//definded Schema PRODUCTS
class ProductTable(tag: Tag) extends Table[Product](tag,"PRODUCTS") {
  def id = column[Long]("ID",O.PrimaryKey, O.AutoInc)
  def ean = column[Long]("EAN")
  def name = column[String]("NAME")
  def description = column[String]("DESCRIPTION")
  //default project method 로 column정보인 tuple을 case class 인 Product로 mapping 해준다.
  def * = (id,ean,name,description).mapTo[Product]
}

@Singleton
class ProductDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {
  
  val db = dbConfigProvider.get[JdbcProfile].db
  
  //defined TableQuery from ProductTable
  //TableQuery extends Query  이다.
  //Query는 monad 이다.
  lazy val productTableQuery = TableQuery[ProductTable]
  
  val init = {
    val mockData = Seq(Product(1,12345,"TEST A","TEST A"),
                       Product(2,22345,"MOCK B","MOCK B"),
                       Product(3,32345,"TEST C","TEST C"),
                       Product(4,42345,"MOCK D","MOCK D"),
                       Product(5,52345,"TEST E","TEST E"))
    val action = productTableQuery.schema.create andThen (productTableQuery ++= mockData)
    db run action
  }
  
  def getAll: Future[Seq[Product]] = {
    val action = productTableQuery.sortBy(pt => pt.name).result
    db run action
  }
  
  def findById(id: Long): Future[Option[Product]] = {
    //query: Query[ProductTable, ProductTable.TableElementType, Seq]
    val query = productTableQuery.filter(pt => pt.id === id)
    
    //DBIO 는 query 실행 결과 및, schema등의 정보가 있으며 실제 atabbase에 실행하는 대상 이다.    
    val action: DBIO[Option[Product]] = query.result.headOption
    
    //실제 query 가 실해됨. 
    db run action
  }
  
  def findByName(name: String): Future[Seq[Product]] = {
    
    //Query[Rep[Boolean], Boolean, Seq]
    val query2 = productTableQuery.map(pt => pt.name like ("%"+name+"%"))
    
    //Query[ProductTable, Product, Seq]
    val query23 = productTableQuery.map(pt => pt)
    
    //Query[Rep[String],String, Seq]
    val query22 = productTableQuery.map(pt => pt.name)
    
    //Query[(Rep[Long], Rep[Boolean]), (Long, Boolean), Seq]
    val query33 = productTableQuery.flatMap(pt => query2.map(b => (pt.id, b)))
    
    //Query[ProductTable, ProductTable, Seq]
    val query3 = productTableQuery.filter(pt => pt.name like ("%" + name +"%"))
    
    //Query[ProductTable, ProductTable.TableElementType, Seq]
    val query = productTableQuery.filter((pt:ProductTable) => pt.name like ("%" + name +"%"))
    val action: DBIO[Seq[Product]] = query.result
    
    db run action
  }
  
  def addProducts(xs: List[Product]): Future[Option[Int]] = {
    val action = productTableQuery ++= xs
    db run action
  }
  
  def executeProducts(xs: List[Product]): Future[Int] = {
    
    val addAction = productTableQuery ++= xs
    val updateAction = productTableQuery.filter(pt => pt.id === 1L).map(pt => pt.description).update("Update Value")
    
    db run (addAction andThen updateAction).transactionally
  }
  
  def executeRollback(xs: List[Product]): Future[Int] = {
    val addAction = productTableQuery ++= xs
    val updateAction = productTableQuery.filter(pt => pt.id === 2L).map(pt => pt.id).update(1L)
    
    db run (addAction andThen updateAction).transactionally
  }
  
  def deleteProduct(id: Long): Future[Int] = {
    val action = productTableQuery.filter(pt => pt.id === id).delete
    db run action
  }
  
}




