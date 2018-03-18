package actors.websocketchapter

import models.websocket._
import akka.actor.Actor
import play.api.libs.iteratee.Concurrent
import play.api.libs.iteratee.Iteratee
import play.api.libs.concurrent.Execution.Implicits._
import play.api.Logger
import play.api.libs.iteratee.Enumerator

class ChatActor extends Actor {

  var users = Set[String]()
  val (enumerator, channel) = Concurrent.broadcast[String]

  def receive = {
    case Join(nick) => {
      Logger.debug(s"###### Actor receive case Join Nick ${nick}")
      if (!users.contains(nick)) {
        val iteratee = Iteratee.foreach[String] { message =>
          Logger.debug(s"##### Operator in Iteratee call Broadcast ${message}")
          self ! Broadcast("%s: %s" format (nick, message))
        }.map { _ =>
          Logger.debug("##### Operator in Iteratee call Leaves")
        }

        Logger.debug("##### add users and push channel")
        users += nick
        channel.push("User %s has joined the room, now %s" format (nick, users.size))
        Logger.debug("##### add users and push channel end and callback sender (iteratee,enumerator)")
        sender ! (iteratee, enumerator)
      } 
      else {
        Logger.debug(s"##### Actor receive Join Nick but not contains user ${nick}")
        val enumerator = Enumerator("Nickname %s is Already in use." format nick)
        Logger.debug("##### Actor receive generate enumerator already in use message")
        val iteratee = Iteratee.ignore
        Logger.debug(s"##### Actor receive generate iteratee already in use message and callback sender(iteratee,enumerator)")
        sender ! (iteratee,enumerator)
      }
    }
    case Leave(nick) => {
      Logger.debug(s"##### Actor receive case Leave message is ${nick}")
      users -= nick
      Logger.debug("##### Actor receive case remove users and push channel message user leave")
      channel.push("User %s has left room, %s users left" format(nick,users.size))
    }
    case Broadcast(msg: String) => {
      Logger.debug(s"##### Actor receive case Broadcase ${msg} and push channel message")
      channel.push(msg)
    }
  }

  
}