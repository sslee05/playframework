package ssleeakka.barcode.models

import scala.util.Try

object Messages {
  
  case class RenderImage(ean: Long)
  case class RenderResult(image: Try[Array[Byte]])
}