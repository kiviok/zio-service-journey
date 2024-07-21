package rohan.config

import zio.Config
import zio.config.magnolia.deriveConfig
import zio.*

case class MigrationConfig(
    cleanMigrate: Boolean
)

object MigrationConfig:
  // *nested* required if in application.conf property key is nested
  val config: Config[MigrationConfig] = deriveConfig[MigrationConfig].nested("migration")

  val layer = ZLayer.fromZIO(
    ZIO.config[MigrationConfig](MigrationConfig.config)
  )
