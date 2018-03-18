package utils

object JsonConvert {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  import play.api.libs.json._

	val json: JsValue = Json.parse("""
	  {
	    "name" : "Watership Down",
	    "location" : {
	      "lat" : 51.235685,
	      "long" : -1.309197
	    },
	    "residents" : [ {
	      "name" : "Fiver",
	      "age" : 4,
	      "role" : null
	    }, {
	      "name" : "Bigwig",
	      "age" : 6,
	      "role" : "Owsla"
	    } ]
	  }
	  """)                                    //> json  : play.api.libs.json.JsValue = {"name":"Watership Down","location":{"l
                                                  //| at":51.235685,"long":-1.309197},"residents":[{"name":"Fiver","age":4,"role":
                                                  //| null},{"name":"Bigwig","age":6,"role":"Owsla"}]}
   
   val json2: JsValue = Json.toJson(42)           //> json2  : play.api.libs.json.JsValue = 42
   val json3: JsValue = Json.toJson(Map(
      "name" -> Json.toJson("Johnny"),
      "age" -> Json.toJson(42)
    ))                                            //> json3  : play.api.libs.json.JsValue = {"name":"Johnny","age":42}
   val rj = (json3 \ " name").as[String]          //> play.api.libs.json.JsResultException: JsResultException(errors:List((,List(J
                                                  //| sonValidationError(List(' name' is undefined on object: {"name":"Johnny","ag
                                                  //| e":42}),WrappedArray())))))
                                                  //| 	at play.api.libs.json.JsReadable.$anonfun$as$2(JsReadable.scala:25)
                                                  //| 	at play.api.libs.json.JsError.fold(JsResult.scala:56)
                                                  //| 	at play.api.libs.json.JsReadable.as(JsReadable.scala:24)
                                                  //| 	at play.api.libs.json.JsReadable.as$(JsReadable.scala:23)
                                                  //| 	at play.api.libs.json.JsUndefined.as(JsLookup.scala:181)
                                                  //| 	at utils.JsonConvert$.$anonfun$main$1(utils.JsonConvert.scala:32)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$anonfun$$ex
                                                  //| ecute$1(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:76)
                                                  //| 	at utils.JsonConvert$.main(utils.JsonConvert.scala:3)
                                                  //| 	at utils.JsonConvert.
                                                  //| Output exceeds cutoff limit.
   case class Location(lat: Double, long: Double)
   case class Resident(name: String, age: Int, role: Option[String])
   case class Place(name: String, location: Location, residents: Seq[Resident])
   
   //import play.api.libs.json._

	 //val json = { ... }
	
	 // Simple path
	 val latPath = JsPath \ "location" \ "lat"
	
	 // Recursive path
	 val namesPath = JsPath \\ "name"
	
	 // Indexed path
	 val firstResidentPath = (JsPath \ "residents")(0)
	 
	 import play.api.libs.json._ // JSON library
	 import play.api.libs.json.Reads._ // Custom validation helpers
	 import play.api.libs.functional.syntax._ // Combinator syntax
   val nameReads: Reads[String] = (JsPath \ "name").read[String]
   val locationReadsBuilder =
     (JsPath \ "lat").read[Double] and
     (JsPath \ "long").read[Double]
   
}