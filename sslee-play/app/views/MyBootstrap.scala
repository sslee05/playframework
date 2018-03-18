package views

object MyBootstrap {
  import views.html.helper.FieldConstructor
  implicit val mybootstrapFieldConstructor = FieldConstructor(html.formchapter.myFieldConstructorTemplate.f)
}