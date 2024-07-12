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
    Method.GET / "customer" / uuid("customerId") / "accounts" -> handler {
      (customerId: UUID, _: Request) =>
        for acc <- accountService.getAccountsForCustomer(CustomerId(customerId))
        yield Response.json(acc.toJson)

    },
    Method.POST / "customer" / "account" / "open" -> handler { (req: Request) =>
      for
        oc <- ServerUtils.parseBody[AccountOpen](req)
        id <- accountService.open(oc)
      yield Response.text(id.toString())
    }
  ).handleError(e => Response.internalServerError(e.toString()))

object AccountRoutes:
  val layer: ZLayer[AccountService, Nothing, AccountRoutes] = ZLayer.derive[AccountRoutes]
