name := "ZenTask"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "0.6.0.1",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.3.0"
)     

play.Project.playScalaSettings

