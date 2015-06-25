import AssemblyKeys._

name := "hivemall-spark"

version := "0.0.1"

scalaVersion := "2.10.4"

// Switch suitable source codes for hive-0.12.x or hive-.13.x
// TODO: Support Hive 0.12
unmanagedSourceDirectories in Compile += baseDirectory.value / "extra-src/v0.13.1"

// Enable sbt-assembly
assemblySettings

net.virtualvoid.sbt.graph.Plugin.graphSettings

// scalacOptions ++= Seq("-language:experimental.macros")

// resolvers += Resolver.sonatypeRepo("releases")
// addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.4",
  "org.apache.spark" % "spark-core_2.10" % "1.4.0",
  "org.apache.spark" % "spark-mllib_2.10" % "1.4.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.4.0",
  "org.apache.spark" % "spark-hive_2.10" % "1.4.0",
  "org.spark-project.hive" % "hive-exec" % "0.13.1a",
  "org.spark-project.hive" % "hive-serde" % "0.13.1a",
  "io.github.myui" % "hivemall" % "0.3.1",
  "org.xerial" % "xerial-core" % "3.2.3"
)

mergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml" =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types" =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".thrift" =>
    MergeStrategy.first
  case "application.conf" =>
    MergeStrategy.concat
  case "unwanted.txt" =>
    MergeStrategy.discard
  case x =>
    val oldStrategy = (mergeStrategy in assembly).value
    oldStrategy(x)
}

