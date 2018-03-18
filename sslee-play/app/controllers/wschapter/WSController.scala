package controllers.wschapter

import scala.concurrent.duration._
import play.api.mvc.ControllerComponents
import play.api.mvc.AbstractController
import play.api.i18n.I18nSupport
import javax.inject.{Inject,Singleton}
import scala.concurrent.ExecutionContext
import scala.concurrent.Await

@Singleton
class WSController @Inject()(cc: ControllerComponents)(ex: ExecutionContext) 
  extends AbstractController(cc) with I18nSupport {
  
}