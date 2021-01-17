lazy val commonSettings = Seq(
  organization := "gg.warcraft",
  version := "16.0.0-SNAPSHOT",
  scalaVersion := "2.13.4",
  scalacOptions ++= Seq(
    // additional scalac options go here
  ),
  resolvers ++= Seq(
    Resolver.mavenLocal
  )
)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := s"${name.value}-${version.value}-all.jar",
  assemblyOption in assembly :=
    (assemblyOption in assembly).value.copy(includeScala = false),
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", _ @_*) => MergeStrategy.discard
    case "module-info.class"         => MergeStrategy.discard
    case it                          => (assemblyMergeStrategy in assembly).value(it)
  }
)

lazy val api = (project in file("gathering-api"))
  .settings(
    name := "gathering-api",
    commonSettings,
    libraryDependencies ++= Seq(
      "gg.warcraft" %% "monolith-api" % "16.0.0-SNAPSHOT" % Provided
    ) ++ Seq(
      "org.scalatest" %% "scalatest" % "3.2.+" % Test
    )
  )

lazy val spigot = (project in file("gathering-spigot"))
  .settings(
    name := "gathering-spigot",
    commonSettings,
    assemblySettings,
    resolvers ++= Seq(
      "PaperMC" at "https://papermc.io/repo/repository/maven-public/"
    ),
    libraryDependencies ++= Seq(
      "com.destroystokyo.paper" % "paper-api" % "1.16.4-R0.1-SNAPSHOT" % Provided,
      "gg.warcraft" %% "monolith-spigot" % "16.0.0-SNAPSHOT" % Provided
    )
  )
  .dependsOn(api)
