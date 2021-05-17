name := "ErrorM"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  // scala
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  // testing
  "junit" % "junit" % "4.11",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test",
  // json
  "com.typesafe.play" %% "play-json" % "2.9.1"
)