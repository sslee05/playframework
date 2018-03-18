name := """ean"""
organization := "studyplay"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "net.sf.barcode4j" % "barcode4j" % "2.1"

publishTo := Some(
 "My resolver" at "https://github.com/sslee05/studyplay.github.io"
)
//credentials += Credentials(
//  "Repository Realm", "htpps://github.com/sslee05/studyplay.github.io","userId","hashed-password"
//)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.sslee.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.sslee.example.binders._"
