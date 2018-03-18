package ssleeakka.barcode.controllers

import play.api.mvc.ControllerComponents
import play.api.mvc.AbstractController
import play.api.i18n.I18nSupport
import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
import org.krysalis.barcode4j.impl.upcean.EAN13Bean
import scala.util.Try
import scala.util.Failure
import scala.util.Success
import scala.concurrent.duration._
import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.pattern.ask
import javax.inject._
import scala.concurrent.duration._
import ssleeakka.barcode.models.Messages._
import akka.util.Timeout
import play.api.Logger
import ssleeakka.barcode.api.Barcodes

@Singleton
class BarcodeController @Inject() (cc: ControllerComponents,barcodes: Barcodes)(implicit ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  def getBarcode(ean: Long) = Action.async {
    barcodes getBarcodeData ean map {
      case Success(data) => Ok(data).as("image/png")
      case Failure(e) => BadRequest("Couldn't generate bar code. Error: " + e.getMessage)
    }
  }

}