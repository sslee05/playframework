package ssleeakka.barcode.actors

import akka.actor.Actor
import scala.concurrent.Future
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
import org.krysalis.barcode4j.impl.upcean.EAN13Bean
import ssleeakka.barcode.models.Messages._
import scala.concurrent.ExecutionContext
import javax.inject.Inject
import play.api.Logger

class BarcodeCacheActor extends Actor {
  
  import play.api.libs.concurrent.Execution.Implicits._
  var imageCache = Map[Long, Future[Array[Byte]]]()
  
  def receive = {
    case RenderImage(ean) => {
      Logger.debug("########4")
      val futureImage = imageCache.get(ean).map(future => future).getOrElse {
        val futureImage = Future { ean13BarCode(ean, "image/png",144)}
        imageCache += (ean -> futureImage)
        futureImage
      }
      
      val client = sender
      futureImage.onComplete { 
        // 이부분의 코드 실행은 이 context에서 실행 되지 않는다. 따라서 위에 client를 위에 잡어서 clojure 로 했다.
        Logger.debug("########5")
        client ! RenderResult(_) 
      }
    }
  }
  
  def ean13BarCode(ean: Long, mimeType: String,imageResolution: Int): Array[Byte] = {
    
    Logger.debug("############ new renderring image")
    import java.io.ByteArrayOutputStream
    import java.awt.image.BufferedImage

    val output = new ByteArrayOutputStream
    val canvas = new BitmapCanvasProvider(output, mimeType,
      imageResolution, BufferedImage.TYPE_BYTE_BINARY, false, 0)

    val barCode = new EAN13Bean
    barCode.generateBarcode(canvas, String valueOf ean)
    canvas.finish()

    output.toByteArray
  }
  
}