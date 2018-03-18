package controllers

import java.util.Date

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import javax.inject.Inject
import models.PickList
import play.api.Logger
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import play.twirl.api.Html

class PickListController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  
  def preview(warehouse: String) = Action { 
    
    val pickList = PickList.find(warehouse)
    val timestamp = new java.util.Date
    Ok(views.html.pickList(warehouse,pickList,timestamp))
  }
  
  def sendAsync(warehouse: String) = Action {
    import scala.concurrent.ExecutionContext.Implicits.global
    
    Future {
      val pickList = PickList.find(warehouse)//big heaven operator
      send(views.html.pickList(warehouse, pickList, new Date()))
    }
    println("#########1")

    //위의 무거운 연산를 기다리지 않고 바로 아래가 실행되어 client에게 보내진다.
    //위의 send는 무거운 연산처리가 끝나면 console로 보내진다.
    Redirect(routes.PickListController.index())
  }
  
  def backlog(warehouse: String) = Action.async {
    import scala.concurrent.ExecutionContext.Implicits.global
    
    val backlog = Future {
      models.Order.backlog(warehouse)//big heavy operation
    }
    println("########1")
    //thread는 위의 무거운 처리를 기다리지 않고 다른 요청를 처리하기 위해
    //thread-pool로 돌아간다.
    //추후 무거운 연산이 끝난 후 아래 코드가 실행되어 client로 reponse 된다.
    backlog.map(value => Ok(value))
  }
  
  
  private def send(html: Html) {
    
    Logger.debug(html.body)
  }
  
  def index = Action {
    
    println("#########2")
    Ok(views.html.indexChapter2("test####################"))
  }
  
}