package rohan.accounts

import rohan.types.CustomerId
import zio.schema.Schema
import zio.schema.DeriveSchema
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

final case class AccountOpen(customerId: CustomerId, accountType: AccountType)

object AccountOpen:
  given Schema[AccountOpen]    = DeriveSchema.gen
  given JsonCodec[AccountOpen] = DeriveJsonCodec.gen
