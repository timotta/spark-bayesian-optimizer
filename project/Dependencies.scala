import sbt._

object Dependencies {

  val sparkVersion = "2.4.0"

  val providedDependencies = Seq[ModuleID](
    "org.apache.spark" %% "spark-core" % sparkVersion exclude("org.scalatest", "scalatest")
  )
  val embeddedDependencies = Seq[ModuleID](
      "org.apache.spark" %% "spark-mllib" % sparkVersion,
      "org.scalanlp" %% "breeze" % "0.12",
      "com.github.danielkorzekwa" %% "bayes-scala" % "0.6",
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
      "com.twitter" % "chill_2.11" % "0.8.0"
  )
  val testsDependencies = Seq[ModuleID](
    "org.scalatest" %% "scalatest" % "3.0.1",
    "org.mockito" % "mockito-core" % "2.22.0",
    "org.apache.spark" %% "spark-hive" % s"${sparkVersion}"
  )

  val rootDependencies = embeddedDependencies ++ providedDependencies.map(_ % Provided) ++ testsDependencies.map(_ % Test)

  val mainRunnerDependencies = (providedDependencies ++ embeddedDependencies ++ testsDependencies).map(_ % Compile)

}
