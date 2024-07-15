package rohan

import zio.ZIO
import org.flywaydb.core.Flyway
import javax.sql.DataSource
import zio.ZLayer

final case class FlywayMigration(datasource: DataSource):
  private lazy val loadFlyway = ZIO.attempt {
    Flyway
      .configure()
      .dataSource(datasource)
      .baselineOnMigrate(true)
      .baselineVersion("0")
      .load()
  }

  val resetMigrate =
    for
      flyway <- loadFlyway
      _ <- ZIO.attempt(flyway.clean())
      _ <- ZIO.attempt(flyway.migrate())

    yield ()

object FlywayMigration:
  val layer = ZLayer.fromFunction(FlywayMigration(_))
