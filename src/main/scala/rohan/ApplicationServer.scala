package rohan

import zio.http.*
import zio.*
import customers.CustomerRoutes
import rohan.accounts.AccountRoutes

final case class ApplicationServer(
    customerRoutes: CustomerRoutes,
    accountRoutes: AccountRoutes
):
  private val serverRoutes = customerRoutes.all ++ accountRoutes.all

  def start: ZIO[Server & FlywayMigration, Throwable, Unit] =
    for
      _ <- FlywayMigration.cleanMigrate
      _ <- Server.serve(serverRoutes)
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
      yield ApplicationServer(cr, ar)
    }
