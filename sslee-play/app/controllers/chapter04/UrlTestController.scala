package controllers.chapter04

import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import play.api.Logger

@Singleton
class UrlTestController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  
  def photo(file: String) = Action {
    Logger.debug("file:"+file)
    //new Status(501)
    //val url = views.html.chapter04.phone(file)
    //Status(SEE_OTHER).withHeaders(LOCATION -> "url")
    Ok(views.html.chapter04.phone(file))
  }
  
  def reguex(param: Long) = Action {
    Logger.debug("reguex:"+ param)
    //Ok(views.html.chapter04.phone(param.toString()))
    import play.api.libs.json.Json
    
    val bean = TestBean("sslee",10)
    val json = Json.obj(
      "name" -> bean.name,
      "age"  -> bean.age
    )
    Ok(json)
  }
  
  case class TestBean(name: String, age: Int) extends Serializable
}