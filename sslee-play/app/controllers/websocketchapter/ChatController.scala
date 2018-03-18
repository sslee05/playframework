package controllers.websocketchapter

import play.api.mvc.ControllerComponents
import play.api.mvc.AbstractController
import akka.actor.ActorSystem
import play.api.i18n.I18nSupport
import akka.stream.Materializer
import javax.inject.{Inject,Singleton,Named}
import scala.concurrent.ExecutionContext
import play.api.mvc.WebSocket
import play.api.libs.json.JsValue
import play.api.libs.json._
import models.websocket.InEvent
import models.websocket.OutEvent
import play.api.mvc.WebSocket.MessageFlowTransformer
import play.api.libs.streams.ActorFlow
import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorRef
import akka.pattern._
import play.api.libs.iteratee.Concurrent
import akka.util.Timeout
import scala.concurrent.duration._
import models.websocket._
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Enumerator
import java.util.stream.Streams
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Source
import akka.pattern.AskableActorRef._
import play.api.libs.iteratee.streams._
import play.api.Logger


@Singleton
class ChatController @Inject()(cc: ControllerComponents, @Named("chatActor") chatActor: ActorRef)(implicit ec: ExecutionContext,mat: Materializer)
  extends AbstractController(cc) with I18nSupport {
  
  implicit val timeout  = Timeout(1 seconds)
  
  def showRoom(nick: String) = Action { implicit requets =>
    Ok(views.html.websocketchapter.showRoom(nick))
  }
  
  def chatSocket(nick: String) = WebSocket.acceptOrResult[String,String] { implicit request =>
    Logger.debug(s"########### Controller Action in request message is ${nick}")
    val channelsFuture = chatActor ? Join(nick)
    
    channelsFuture.mapTo[(Iteratee[String,_],Enumerator[String])].map( res => {
      val source = Source.fromPublisher(IterateeStreams.enumeratorToPublisher(res._2))
      val (subscriber, resultIteratee) = IterateeStreams.iterateeToSubscriber(res._1)
      val sink = Sink.fromSubscriber(subscriber)
      Right(Flow.fromSinkAndSource(sink,source))
    })
  }
}

/*
[debug] application - ########### Controller Action in request message is sslee
[debug] application - ###### Actor receive case Join Nick sslee
[debug] application - ##### add users and push channel
[debug] application - ##### add users and push channel end and callback sender (iteratee,enumerator)

[debug] application - ########### Controller Action in request message is sslee2
[debug] application - ###### Actor receive case Join Nick sslee2
[debug] application - ##### add users and push channel
[debug] application - ##### add users and push channel end and callback sender (iteratee,enumerator)

[debug] c.z.h.p.HikariPool - db - Pool stats (total=20, active=0, idle=20, waiting=0)
[debug] application - ##### Operator in Iteratee call Broadcast Hi
[debug] application - ##### Actor receive case Broadcase sslee: Hi and push channel message

[debug] application - ##### Operator in Iteratee call Broadcast HI TWO
[debug] application - ##### Actor receive case Broadcase sslee2: HI TWO and push channel message
*/