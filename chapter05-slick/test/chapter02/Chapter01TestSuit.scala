package chapter02

import slick.jdbc.H2Profile.api._
import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatestplus.play.PlaySpec
import play.api.test.Injecting
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import slick.lifted.Tag


object Chapter01TestSuit extends PlaySpec with GuiceOneAppPerTest with Injecting {
  
  //define case class representing row data 
  final case class Message(sender: String, content: String, id: Long = 0L)
  
  //definded Schema for the "message" table:
  class MessageTable(tag: Tag) extends Table[Message](tag,"MESSAGE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def sender = column[String]("SENDER")
    def content = column[String]("CONTENT")
    def * = (sender,content,id).mapTo[Message]
  }
  
  lazy val messages = TableQuery[MessageTable]
  val hals = messages.filter(mt => mt.sender === "HAL")
  // Create an in-memory H2 database;
  val db = Database.forConfig("default")

  // Helper method for running a query in this example file:
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2.seconds)
  
  val test = for {
    me <- messages if me.sender === "HAL"
  }yield(me)
  
  val aa = messages.map(mt => mt.content)
  val xs = List(1,2,3)
  val rx = xs.map(a => a.toString()) 

  
  
}