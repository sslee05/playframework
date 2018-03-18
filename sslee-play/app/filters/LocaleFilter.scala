package filters

import play.api.mvc.RequestHeader
import play.api.libs.streams.Accumulator
import play.api.mvc.EssentialFilter
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import play.api.mvc.EssentialAction
import akka.util.ByteString
import play.api.mvc.Result
import play.api.Logger
import play.api.i18n.MessagesApi

class LocaleFilter @Inject() (implicit ec: ExecutionContext,messagesApi: MessagesApi) extends EssentialFilter {
  
  
  def apply(nextFilter: EssentialAction) = new EssentialAction {
    def apply(requestHeader: RequestHeader) = {
      println("############################")
      val startTime = System.currentTimeMillis

      val accumulator: Accumulator[ByteString, Result] = nextFilter(requestHeader)

      accumulator.map { result =>

        val endTime = System.currentTimeMillis
        val requestTime = endTime - startTime

        Logger.info(s"${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}")
        result.withHeaders("Request-Time" -> requestTime.toString)

      }
    }
  }
}

