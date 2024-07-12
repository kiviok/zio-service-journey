package rohan.accounts

import rohan.types.CustomerId
import zio.schema.Schema
import zio.schema.DeriveSchema
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec
import neotype.interop.ziojson.given

final case class AccountOpen(customerId: CustomerId, accountType: AccountType)
    derives JsonCodec
