package rohan

import zio.*
import org.flywaydb.core.Flyway

import javax.sql.DataSource
import rohan.config.MigrationConfig

final case class FlywayMigration(datasource: DataSource, config: MigrationConfig):
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
    config.cleanMigrate match
      case true =>
        for
          flyway <- loadFlyway
          _      <- ZIO.attempt(flyway.clean())
          _      <- ZIO.attempt(flyway.migrate())
        yield ()
      case false => ZIO.debug("Starting without migrations...")

object FlywayMigration:
  val layer = ZLayer.derive[FlywayMigration]

  def cleanMigrate: ZIO[FlywayMigration, Throwable, Unit] =
    ZIO.serviceWithZIO[FlywayMigration](_.cleanMigrate)
