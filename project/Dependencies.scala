import sbt._
object Dependencies {

  val sparkVersion = "2.4.0"

  val providedDependencies = Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion exclude("org.scalatest", "scalatest"),
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" %% "spark-yarn" % sparkVersion,
    "org.apache.spark" %% "spark-mllib" % sparkVersion
  )
  val embeddedDependencies = Seq(
  )
  val testsDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1",
    "org.mockito" % "mockito-core" % "2.22.0",
    "org.apache.spark" %% "spark-hive" % s"${sparkVersion}"
  )

  val rootDependencies = embeddedDependencies ++ providedDependencies.map(_ % Provided) ++ testsDependencies.map(_ % Test)

  val mainRunnerDependencies = (embeddedDependencies ++ providedDependencies ++ testsDependencies).map(_ % Compile)

}
