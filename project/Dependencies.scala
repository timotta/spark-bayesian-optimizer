import sbt._

object Dependencies {

  val sparkVersion = "2.4.0"

  val providedDependencies = Seq[ModuleID](
    "org.apache.spark" %% "spark-core" % sparkVersion exclude("org.scalatest", "scalatest"),
    "org.apache.spark" %% "spark-mllib" % sparkVersion
  )
  val embeddedDependencies = Seq[ModuleID](

  )
  val testsDependencies = Seq[ModuleID](
    "org.scalatest" %% "scalatest" % "3.0.1",
    "org.mockito" % "mockito-core" % "2.22.0",
    "org.apache.spark" %% "spark-hive" % s"${sparkVersion}"
  )

  //TODO: not using it, verify in the future
  val rootDependencies = embeddedDependencies ++ providedDependencies.map(_ % Provided) ++ testsDependencies.map(_ % Test)

  val mainRunnerDependencies = (providedDependencies ++ embeddedDependencies ++ testsDependencies).map(_ % Compile)

}
