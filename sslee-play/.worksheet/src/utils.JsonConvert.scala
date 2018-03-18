package utils

object JsonConvert {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(79); 
  println("Welcome to the Scala worksheet")
  
  import play.api.libs.json._;$skip(374); 

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
	  """);System.out.println("""json  : play.api.libs.json.JsValue = """ + $show(json ));$skip(44); 
   
   val json2: JsValue = Json.toJson(42);System.out.println("""json2  : play.api.libs.json.JsValue = """ + $show(json2 ));$skip(118); 
   val json3: JsValue = Json.toJson(Map(
      "name" -> Json.toJson("Johnny"),
      "age" -> Json.toJson(42)
    ));System.out.println("""json3  : play.api.libs.json.JsValue = """ + $show(json3 ));$skip(41); 
   val rj = (json3 \ " name").as[String]
   case class Location(lat: Double, long: Double)
   case class Resident(name: String, age: Int, role: Option[String])
   case class Place(name: String, location: Location, residents: Seq[Resident]);System.out.println("""rj  : String = """ + $show(rj ));$skip(323); 
   
   //import play.api.libs.json._

	 //val json = { ... }
	
	 // Simple path
	 val latPath = JsPath \ "location" \ "lat";System.out.println("""latPath  : play.api.libs.json.JsPath = """ + $show(latPath ));$skip(57); 
	
	 // Recursive path
	 val namesPath = JsPath \\ "name";System.out.println("""namesPath  : play.api.libs.json.JsPath = """ + $show(namesPath ));$skip(72); 
	
	 // Indexed path
	 val firstResidentPath = (JsPath \ "residents")(0)
	 
	 import play.api.libs.json._ // JSON library
	 import play.api.libs.json.Reads._ // Custom validation helpers
	 import play.api.libs.functional.syntax._;System.out.println("""firstResidentPath  : play.api.libs.json.JsPath = """ + $show(firstResidentPath ));$skip(243);  // Combinator syntax
   val nameReads: Reads[String] = (JsPath \ "name").read[String];System.out.println("""nameReads  : play.api.libs.json.Reads[String] = """ + $show(nameReads ));$skip(105); 
   val locationReadsBuilder =
     (JsPath \ "lat").read[Double] and
     (JsPath \ "long").read[Double];System.out.println("""locationReadsBuilder  : play.api.libs.functional.FunctionalBuilder[play.api.libs.json.Reads]#CanBuild2[Double,Double] = """ + $show(locationReadsBuilder ))}
   
}
