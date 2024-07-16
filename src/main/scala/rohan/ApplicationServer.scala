package rohan

import zio.http.*
import zio.*
import customers.CustomerRoutes
import rohan.accounts.AccountRoutes

final case class ApplicationServer(
    customerRoutes: CustomerRoutes,
    accountRoutes: AccountRoutes,
    migration: FlywayMigration
):
  private val serverRoutes = customerRoutes.all ++ accountRoutes.all

  def start: Task[Unit] =
    for
      _ <- migration.cleanMigrate
      _ <- Server.serve(serverRoutes).provide(Server.default)
    yield ()

object ApplicationServer:
  val layer: ZLayer[
    FlywayMigration & AccountRoutes & CustomerRoutes,
    Nothing,
    ApplicationServer
  ] =
    ZLayer {
      for
        cr <- ZIO.service[CustomerRoutes]
        ar <- ZIO.service[AccountRoutes]
        m  <- ZIO.service[FlywayMigration]
      yield ApplicationServer(cr, ar, m)
    }
