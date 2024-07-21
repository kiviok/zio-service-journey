package rohan

import zio.*

import java.io.IOException
import zio.http.*
import io.getquill.jdbczio.Quill
import io.getquill.SnakeCase
import customers.*
import rohan.accounts.AccountRoutes
import rohan.accounts.AccountServiceLive
import zio.config.typesafe.TypesafeConfigProvider
import rohan.config.MigrationConfig

object MainApp extends ZIOAppDefault:

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.setConfigProvider(TypesafeConfigProvider.fromResourcePath())

  def run = ZIO
    .serviceWithZIO[ApplicationServer](_.start)
    .provide(
      Server.default,
      ApplicationServer.layer,
      CustomerRoutes.layer,
      AccountRoutes.layer,
      CustomerServiceLive.layer,
      AccountServiceLive.layer,
      MigrationConfig.layer,
      FlywayMigration.layer,
      Quill.Postgres.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("datasource")
    )
    .debug("Results")
    .exitCode
