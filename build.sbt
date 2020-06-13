name := """Test"""
organization := "Me"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.41",
  "com.typesafe.slick" %% "slick" % "3.3.2",
)
