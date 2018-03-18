package controllers.websocketchapter

import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext
import play.api.i18n.I18nSupport
import play.api.mvc.WebSocket
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Enumerator
import scala.concurrent.impl.Promise
import scala.concurrent.Promise
import scala.concurrent.Future
import sys.process._
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Sink
import play.api.Logger
import akka.actor._
import akka.stream.Materializer
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow

//import scala.concurrent.Promise

@Singleton
class WebSocketSampleController @Inject() (cc: ControllerComponents)(implicit ec: ExecutionContext,mat: Materializer,system: ActorSystem)
  extends AbstractController(cc) with I18nSupport {

  def getView = Action { implicit request =>
    Ok(views.html.websocketchapter.websocketstatus())
  }
  
  def statusFeed() = WebSocket.accept[String,String] { request =>
    
    val sumSink = Sink.fold(0) { (sum: Int, chunk: Int) =>
      println("Sink calculate"+ chunk)
      Thread.sleep(1000l)
      sum + chunk
    }
    
    ActorFlow.actorRef { out =>
      Logger.debug("############ statusFeed method called")
      MyWebSocketActor.props(out)
    }
  }
  
  object MyWebSocketActor {
    def props(out: ActorRef) = Props(new MyWebSocketActor(out))
  }

  class MyWebSocketActor(out: ActorRef) extends Actor {
    Logger.debug("####### Actor Instance =>"+this.toString)
    def receive = {
      case msg: String =>
        out ! ("I received your message: " + msg)
    }
  }
  
  def _statusFeed() = WebSocket.accept[String, String] { implicit request =>
    //val out = Source.fromFuture(Future{ getSystemInfo })
    //Flow.fromSinkAndSource(in, out)
    Logger.debug("############ statusFeed method called")

    /*
    // Just ignore the input
    val in = Sink.ignore

    // Send a single 'Hello!' message and close
    val out = Source.single("Hello!")

    Flow.fromSinkAndSource(in, out)
    */
    
    Flow[String].map { msg =>
      println(msg)
      "I received your message: " + msg
    }
  }

  def getSystemInfo = {
    val p = Process("top -F -R -o cpu")
    p.lineStream
  }
}