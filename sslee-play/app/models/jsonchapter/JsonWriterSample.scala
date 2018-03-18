package models.jsonchapter

import play.api.libs.json.Writes
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.functional.syntax._



object JsonWriterSample {
  
  case class Group(groupName: String, createDate: String)
  case class User(id: Long, name: String, groups: List[Group])
  
  val groupWriter = new Writes[Group] {
    def writes(group: Group): JsValue = Json.obj (
      "groupName" -> Json.toJson(group.groupName),
      "createDate" -> Json.toJson(group.createDate)
    )
  }
  
 
  implicit def groupReads: Reads[Group] = (
      (JsPath \ "groupName").read[String] and
      (JsPath \ "createDate").read[String]
   )(Group.apply _)
   
  implicit def  groupWrites: Writes[Group] = (
    (JsPath \ "groupName").write[String] and
    (JsPath \ "createDate").write[String]
  )(unlift(Group.unapply))
  
}