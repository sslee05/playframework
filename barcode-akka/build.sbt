name := """barcode-akka"""
organization := "sslee.playakka"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "net.sf.barcode4j" % "barcode4j" % "2.1"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "sslee.playakka.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "sslee.playakka.binders._"
