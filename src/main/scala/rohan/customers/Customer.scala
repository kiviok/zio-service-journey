package rohan.customers

import java.util.UUID
import rohan.types.*
import zio.schema.Schema
import zio.schema.DeriveSchema
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec
import zio.*
import zio.json.JsonEncoder
import zio.json.DeriveJsonEncoder
import neotype.interop.ziojson.given

final case class Customer(
    id: CustomerId,
    firstName: String,
    lastName: String,
    address: String,
    phone: String,
    email: String,
    passport: Int
) derives JsonCodec

object Customer:
  def make(command: CreateCustomer): UIO[Customer] = Random.nextUUID.map(uuid =>
    Customer(
      CustomerId.unsafeMake(uuid),
      command.firstName,
      command.lastName,
      command.address,
      command.phone,
      command.email,
      command.passport
    )
  )
