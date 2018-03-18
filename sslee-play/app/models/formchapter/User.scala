package models.formchapter

case class User (userName: String, realName: Option[String], email: String, groups: List[Group]) 
  
