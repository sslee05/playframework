package models.websocket

import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class InEvent(name: String, message: String)
case class OutEvent(name: String, message: String)

case class Join(nick: String)
case class Leave(nick: String)
case class Broadcast(message: String)

class WebSocketMessage {
  
  implicit val InEventReads:Reads[InEvent] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "message").read[String]
  )(InEvent.apply _)
  
  implicit val OutEventWrites:Writes[OutEvent] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "message").write[String]
  )(unlift(OutEvent.unapply))
  
}