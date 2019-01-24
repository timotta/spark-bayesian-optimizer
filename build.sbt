import ReleaseTransformations._
val compilerVersion = "2.11.0"

envVars in Test := Map("LC_ALL" -> "en_US.UTF-8")

val buildSettings = Seq(
  name := "spark-bayesian-optimizer",
  scalaVersion := compilerVersion,
  description := "Spark Bayesian Optimizer",
  organization := "com.timotta",
  organizationName := "Globo.com",
  homepage := Some(url("http://www.globo.com")),
  resolvers := Resolvers.defaultResoulvers,
  libraryDependencies ++= Dependencies.mainRunnerDependencies,
  javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
  //run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in(Compile, run), runner in(Compile, run)).evaluated,
  fork := true,
  parallelExecution in Test := false,
  scalaBinaryVersion in ThisBuild := "2.11",
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)

/** ***** Life hacks for:
  * 1. Assembly jar with correct dependencies
  * 2. Run spark jobs in IDEs
  * 3. Run spark jobs in SBT
  * */
lazy val root = (project in file(".")).settings(buildSettings: _*).settings(baseAssemblySettings: _*)

lazy val mainRunner = project.in(file("mainRunner")).dependsOn(RootProject(file("."))).settings(
  libraryDependencies ++= Dependencies.mainRunnerDependencies,
  resolvers := Resolvers.defaultResoulvers,
  scalaVersion := compilerVersion,
  fork := true,
  javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
  parallelExecution in Test := false
)

mainClass in (Compile, run) := Some("com.timotta.spark.cv.Main")
