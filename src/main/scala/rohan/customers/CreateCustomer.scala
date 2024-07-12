package rohan.customers

import zio.json.DeriveJsonCodec
import zio.json.JsonCodec
import zio.schema.Schema
import zio.schema.DeriveSchema

final case class CreateCustomer(
    firstName: String,
    lastName: String,
    address: String,
    email: String,
    phone: String,
    passport: Int
)

object CreateCustomer:
  given Schema[CreateCustomer]    = DeriveSchema.gen
  given JsonCodec[CreateCustomer] = DeriveJsonCodec.gen[CreateCustomer]
