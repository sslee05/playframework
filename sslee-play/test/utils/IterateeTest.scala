package utils

import play.api.libs.iteratee.Iteratee
import scala.concurrent.Future
import play.api.libs.iteratee.Enumerator
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Flow
import play.api.libs.streams.Accumulator

object IterateeTest extends App {
  
  import play.api.libs.concurrent.Execution.Implicits._
  
  val summingIteratee = Iteratee.fold(0) { (sum: Int, chunk: Int) => 
    println("Iteratee calculate"+ chunk)
    Thread.sleep(1000l)
    sum + chunk
    
  }
  
  val intEnumerator = Enumerator(1,2,3,4,5,6,7,8,9,10)
  val newIterateeFuture: Future[Iteratee[Int,Int]] = intEnumerator(summingIteratee)
  val resultFuture: Future[Int] = newIterateeFuture flatMap (_.run)
  resultFuture.onComplete {sum =>
    println("call Future onComplated")
    println("The sum is %d" format sum.get) 
  }
  
  //Thread.sleep(15000l)
  
  //############################################################################
  import akka.actor.ActorSystem
  import akka.stream.ActorMaterializer
  
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  
  val sumSink = Sink.fold(0) { (sum: Int, chunk: Int) =>
    println("Sink calculate"+ chunk)
    Thread.sleep(1000l)
    sum + chunk
  }
  
  val source = Source(1 to 10)
  val rs = source.to(sumSink)
  val tt = source.runWith(sumSink)
  
  tt.onComplete  {sum =>
    println("call Flow Future onComplated")
    println("The sum is %d" format sum.get) 
  }
  
  println("####### start ########")
}