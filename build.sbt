name := "ZenTask"

version := "1.0-SNAPSHOT"

includeFilter in (Assets, LessKeys.less) := "*.less"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "0.6.0.1",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.3.0"
)     

