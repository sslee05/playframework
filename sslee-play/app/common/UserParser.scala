package common

import play.api.libs.streams.Accumulator
import play.api.mvc.BodyParser
import play.api.mvc.Request
import akka.util.ByteString
import play.api.mvc.Result
import play.api.mvc.RequestHeader
import scala.concurrent.ExecutionContext
import akka.stream.scaladsl.Framing
import akka.stream.scaladsl.Keep
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Flow
import scala.concurrent.Future

/*
class UserParser[T] extends BodyParser[T] {
  def apply[T](requestHeader: RequestHeader)(implicit request: Request[T]) : BodyParser[UserRequest[T]] = {
    BodyParser("api parser") {requestHeader => 
      Accumulator.done(Right[play.api.mvc.Result,UserRequest[T]](UserRequest("sslee",request)))
    }
  }
  
  //def apply[UserRequest](req: RequestHeader) :Accumulator[ByteString, Either[Result, UserRequest[UserRequest]]] = ???
  def apply[UserRequest](req: RequestHeader) :Accumulator[ByteString, Either[Result, UserRequest]] = {
    //BodyParser("api parser") {requestHeader => 
      Accumulator.done(Right[play.api.mvc.Result,UserRequest](UserRequest("sslee",wrapped(req))))
    //}
  }
}
*/
object RequestArchiver {
  def wrappedBodyParser[UserRequest](wrapped: BodyParser[UserRequest])  (implicit exCtx: ExecutionContext): BodyParser[UserRequest] =
    BodyParser("raw-memo") { (req: RequestHeader) =>
      wrapped(req)
  }
}  
  
  
 




