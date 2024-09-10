val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Scala 3 Project Template", 
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
    )
  )
