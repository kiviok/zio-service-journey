package rohan.customers

import neotype.interop.ziojson.given
import rohan.types.*
import zio.*
import zio.json.JsonCodec

import java.util.UUID

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
