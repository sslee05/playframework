name := """chapter05-slick"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies ++= Seq(
  //jdbc,
  ws,
  evolutions,
  
  //bootstrap
  "org.webjars" % "bootstrap" % "3.1.1-2",
  "net.sf.barcode4j" % "barcode4j" % "2.1",
  
  //h2 database
  "com.h2database" % "h2" % "1.4.196",

  //play-slick
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
  
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
