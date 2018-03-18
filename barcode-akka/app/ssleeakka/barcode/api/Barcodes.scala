package ssleeakka.barcode.api

import javax.inject.Inject
import ssleeakka.barcode.models.Messages._
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Try
import akka.actor.ActorRef
import javax.inject._
import scala.concurrent.Future

@Singleton
class Barcodes @Inject() (@Named("barcodeCacheActor") barcodeCacheActor: ActorRef)(implicit ec: ExecutionContext) {
  
  implicit val timeout = Timeout(10 seconds)
  
  def getBarcodeData(ean: Long): Future[Try[Array[Byte]]] = {
    barcodeCacheActor ? RenderImage(ean) map { 
      case RenderResult(data) => data
    }
  }
  
}