package rohan

import zio.http.*
import zio.ZIO
import zio.ZLayer
import customers.CustomerRoutes
import rohan.accounts.AccountRoutes

final case class ApplicationServer(
    customerRoutes: CustomerRoutes,
    accountRoutes: AccountRoutes
):
  val serverRoutes = customerRoutes.all ++ accountRoutes.all

  def start: ZIO[Any, Throwable, Unit] =
    Server.serve(serverRoutes).provide(Server.default)

object ApplicationServer:
  val layer: ZLayer[CustomerRoutes & AccountRoutes, Nothing, ApplicationServer] =
    ZLayer.derive[ApplicationServer]
