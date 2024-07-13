ThisBuild / scalaVersion     := "3.4.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "ru.rohan"
ThisBuild / organizationName := "rohan"

val zioVersion        = "2.1.6"
val zioHttpVersion    = "3.0.0-RC9"
val zioJsonVersion    = "0.7.1"
val zioSchemaVersion  = "1.2.2"
val zioQuillVersion   = "4.8.4"
val postgresqlVersion = "42.7.3"
val flywayVersion     = "10.15.2"
val neoTypeVersion    = "0.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "kung-fu-trader",
    libraryDependencies ++= List(
      "dev.zio"       %% "zio"             % zioVersion,
      "dev.zio"       %% "zio-streams"     % zioVersion,
      "dev.zio"       %% "zio-http"        % zioHttpVersion,
      "dev.zio"       %% "zio-json"        % zioJsonVersion,
      "dev.zio"       %% "zio-schema-json" % zioSchemaVersion,
      "io.getquill"   %% "quill-jdbc-zio"  % zioQuillVersion,
      "org.postgresql" % "postgresql"      % postgresqlVersion,
      // "org.flywaydb"          % "flyway-core"                % flywayVersion,
      // "org.flywaydb"          % "flyway-database-postgresql" % flywayVersion  % "runtime",
      "io.github.kitlangton" %% "neotype"           % neoTypeVersion,
      "io.github.kitlangton" %% "neotype-zio-json"  % neoTypeVersion,
      "io.github.kitlangton" %% "neotype-zio-quill" % neoTypeVersion,
      "dev.zio"              %% "zio-test"          % zioVersion     % Test,
      "dev.zio"              %% "zio-test-sbt"      % zioVersion     % Test,
      "dev.zio"              %% "zio-test-magnolia" % zioVersion     % Test,
      "dev.zio"              %% "zio-http-testkit"  % zioHttpVersion % Test
    )
  )
  .enablePlugins(FlywayPlugin)
  .settings(
    flywayUrl      := "jdbc:postgresql://localhost:25432/test",
    flywayUser     := "test",
    flywayPassword := "test"
  )
