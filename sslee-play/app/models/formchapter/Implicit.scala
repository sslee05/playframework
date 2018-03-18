package models.formchapter

import play.api.data.FormError
import scala.util.Try
import org.joda.time.LocalDate
import play.api.data.format.Formatter

object Implicit {
  implicit val localDateFormat = new Formatter[LocalDate] {
    def bind(key: String, data: Map[String,String]):Either[Seq[FormError],LocalDate] = data.get(key) map { value =>
        Try {
          Right(LocalDate.parse(value))
        } getOrElse Left(Seq(FormError(key,"error.date",Nil)))
    } getOrElse Left(Seq(FormError(key,"error.required",Nil)))
    
    def unbind(key: String, ld: LocalDate): Map[String,String] = Map(key -> ld.toString)
    
    override val format = Some(("date.format", Nil))
  }
}