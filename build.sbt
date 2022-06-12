import Dependencies._
import Util._
//import com.reactific.sbt._

ThisBuild / organization := "finalibre"
ThisBuild / scalaVersion := "3.1.2"
ThisBuild / useSuperShell := false


lazy val `finalibre-dinero` =
  project
    .in(file("."))
    .aggregate(domain, application, integration, persistence, service)
    .settings(
      name := "finalibre-dinero"
    )

lazy val domain =
  project
    .in(file("domain"))
	.disablePlugins(OpenApiCodeGenPlugin)
    .settings(commonSettings)

lazy val application =
  project
    .in(file("application"))
    .dependsOn(domain % Cctt)
	.disablePlugins(OpenApiCodeGenPlugin)
    .settings(commonSettings)
    .settings(commonTestDependencies)

lazy val integration =
  project
    .in(file("integration"))
    .dependsOn(application % Cctt)
    .settings(commonSettings)
	.settings(
	  codegenType := "scala",
	  openApiSpec := file("./integration/src/main/resources/swagger-dinero.json")//,
	  //codegenConfigFile := file("file:///C:/git/finalibre-dinero.dk/integration/src/main/resources/swagger-codegen-config.json")
	)
    .settings(commonTestDependencies)

lazy val persistence =
  project
    .in(file("persistence"))
    .dependsOn(application % Cctt)
	.disablePlugins(OpenApiCodeGenPlugin)
    .settings(commonSettings)
    .settings(commonTestDependencies)

lazy val service =
  project
    .in(file("service"))
    .dependsOn(integration % Cctt)
    .dependsOn(persistence % Cctt)
	.disablePlugins(OpenApiCodeGenPlugin)
    .settings(commonSettings)

lazy val commonTestDependencies = Seq(
  libraryDependencies ++= Seq(
//    com.github.alexarchambault.`scalacheck-shapeless_1.15`,
    org.scalacheck.scalacheck,
    org.scalatest.scalatest,
//    org.scalatestplus.`scalacheck-1-15`,
    org.typelevel.`discipline-scalatest`,
  ).map(_ % Test)
)

lazy val commonSettings =
  commonScalacOptions ++ Seq(
    update / evictionWarningOptions := EvictionWarningOptions.empty
  )

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions := {
    (Compile / console / scalacOptions)
      .value
      .filterNot(_.contains("wartremover"))
      .filterNot(Scalac.Lint.toSet)
      .filterNot(Scalac.FatalWarnings.toSet) :+ "-Wconf:any:silent"
  },
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)
dependencyOverrides += "io.swagger.codegen.v3" % "swagger-codegen-cli" % "3.0.34"

addCommandAlias("gen", "finalibre-dinero/g8Scaffold")

onLoadMessage +=
  s"""\nRun ${green("gen usecase")} to generate new use cases.\n"""

def green(input: Any): String =
  scala.Console.GREEN + input + scala.Console.RESET
