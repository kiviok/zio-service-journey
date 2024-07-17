package rohan.accounts

import zio.http.*
import java.util.UUID
import rohan.types.CustomerId
import zio.json.EncoderOps
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec
import zio.ZLayer
import rohan.ServerUtils

final case class AccountRoutes(accountService: AccountService):

  val all = Routes(
    // Get all accounts for given customerId
    Method.GET / "customers" / uuid("customerId") / "accounts" -> handler {
      (customerId: UUID, _: Request) =>
        for acc <- accountService.getAllByCustomerId(CustomerId(customerId))
        yield Response.json(acc.toJson)

    },
    Method.POST / "accounts" -> handler { (req: Request) =>
      for
        oc <- ServerUtils.parseBody[AccountCreate](req)
        id <- accountService.create(oc)
      yield Response.text(id.toString())
    },
    Method.PATCH / "accounts" -> handler { (req: Request) =>
      for
        au <- ServerUtils.parseBody[AccountUpdate](req)
        _ <- accountService.update(
          au
        ) // TODO add logs and quill exception handlers  -- .catchAll(c => zio.Console.printLine(c))
      yield Response.ok
    }
  ).handleError(e => Response.internalServerError(e.toString()))

object AccountRoutes:
  val layer: ZLayer[AccountService, Nothing, AccountRoutes] = ZLayer.derive[AccountRoutes]
