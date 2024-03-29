import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "BadTaxiPlay"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "org.scala-lang" % "scala-swing" % "2.9.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
