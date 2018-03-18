name := """sslee-play"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
//libraryDependencies += "sslee.playakka" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies ++= Seq(
  //"org.webjars" % "bootstrap" % "3.1.1-2",
  "org.webjars" % "bootstrap" % "4.0.0-1",
  "org.webjars" % "jquery" % "3.3.1-1",
  "net.sf.barcode4j" % "barcode4j" % "2.1",
  
  //h2 database
  "com.h2database" % "h2" % "1.4.196",
  
  //play-slick
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  
  //coffeescript
  "org.webjars.npm" % "coffee-script" % "1.11.0",
  "org.webjars.npm" % "mkdirp" % "0.5.0",
  
  //Iteratee
  "com.typesafe.play" %% "play-iteratees" % "2.6.1",
  //Reactive stream
  "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.1",
  
  //module study lib 
  //groupId, module 명, version
  "sslee.playakka" %% "barcode-akka" % "1.0-SNAPSHOT"
  
)

//less file include 
includeFilter in (Assets, LessKeys.less) := "*.less"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
//play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
