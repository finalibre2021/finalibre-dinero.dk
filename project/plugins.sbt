ThisBuild / scalaVersion := "2.12.15"
ThisBuild / useSuperShell := false
ThisBuild / autoStartServer := false

update / evictionWarningOptions := EvictionWarningOptions.empty

addDependencyTreePlugin

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.34")
addSbtPlugin("com.timushev.sbt" % "sbt-rewarn" % "0.1.3")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.2")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.16")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.13.1")

lazy val root = (project in file(".")).dependsOn(codeGenSbt)

lazy val codeGenSbt = 
  new ProjectRef(uri("file:///C:/git/finalibre-sbt-openapi-codegen"), "finalibre-sbt-openapi-codegen")

  //new ProjectRef(uri("https://github.com/finalibre2021/finalibre-sbt-openapi-codegen.git"), "finalibre-sbt-openapi-codegen")

