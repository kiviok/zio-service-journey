package rohan.customers

import zio.json.JsonCodec
import rohan.types.CustomerId
import neotype.interop.ziojson.given

final case class CreateCustomer(
    firstName: String,
    lastName: String,
    address: String,
    email: String,
    phone: String,
    passport: Int
) derives JsonCodec

final case class UpdateCustomer(
    id: CustomerId,
    firstName: Option[String],
    lastName: Option[String],
    address: Option[String],
    email: Option[String],
    phone: Option[String],
    passport: Option[Int]
) derives JsonCodec
