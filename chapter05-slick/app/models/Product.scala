package models

//deprecated
import scala.concurrent.Future

import javax.inject.{Inject,Singleton}
import play.api.db.slick.DatabaseConfigProvider
//import slick.driver.H2Driver.api._
import slick.jdbc.H2Profile.api._

//model
case class Product(id: Long, ean: Long, name: String, description: String)

//table
class ProductTable(tag: Tag) extends Table[Product](tag,"PRODUCTS")  {
  
  def id = column[Long]("ID",O.PrimaryKey, O.AutoInc)
  def ean = column[Long]("EAN")
  def name = column[String]("NAME")
  def description = column[String]("DESCRIPTION")
  def * = (id,ean,name,description).mapTo[Product]
     
}

@Singleton
class ProductDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider){
  
  val productsTableQuery: slick.lifted.TableQuery[ProductTable] = TableQuery[ProductTable]
  val db = dbConfigProvider.get.db
  
  def findByName(name: String): Future[Seq[Product]] = {
    val query = productsTableQuery.filter(_.name === name)
    val action:DBIO[Seq[Product]] = query.result
    db run action
  }
  
  def adds(xs: Seq[Product]): Future[Option[Int]] = {
    val action = productsTableQuery ++= xs
    db run action
  }
  
  def execute(xs: Seq[Product]) = {
    val action = productsTableQuery ++= xs
    val updateAction = productsTableQuery.filter(_.name === "B").map(p => p.description).update("TEST B B")
    
    db.run(action.andThen(updateAction).transactionally)
  }
}

