package rohan

import zio.*
import org.flywaydb.core.Flyway

import javax.sql.DataSource

final case class FlywayMigration(datasource: DataSource):
  private lazy val loadFlyway = ZIO.attempt {
    Flyway
      .configure()
      .dataSource(datasource)
      .baselineOnMigrate(true)
      .baselineVersion("0")
      .cleanDisabled(false) // for development
      .load()
  }

  val cleanMigrate: Task[Unit] =
    for
      flyway <- loadFlyway
      _      <- ZIO.attempt(flyway.clean())
      _      <- ZIO.attempt(flyway.migrate())
    yield ()

object FlywayMigration:
  val layer = ZLayer.fromFunction(FlywayMigration(_))
