package rohan

import zio.*

import java.io.IOException
import zio.http.*
import io.getquill.jdbczio.Quill
import io.getquill.SnakeCase
import customers.*
import rohan.accounts.AccountRoutes
import rohan.accounts.AccountServiceLive

object MainApp extends ZIOAppDefault:
  def run = ZIO
    .serviceWithZIO[ApplicationServer](_.start)
    .provide(
      ApplicationServer.layer,
      CustomerRoutes.layer,
      AccountRoutes.layer,
      CustomerServiceLive.layer,
      AccountServiceLive.layer,
      FlywayMigration.layer,
      Quill.Postgres.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("datasource")
    )
    .debug("Results")
    .exitCode
