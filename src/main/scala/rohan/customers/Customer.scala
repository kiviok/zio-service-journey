package rohan.customers

import java.util.UUID
import rohan.types.*
import zio.schema.Schema
import zio.schema.DeriveSchema
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec
import zio.*
import zio.json.JsonEncoder
import zio.http.Header.Custom
import zio.json.DeriveJsonEncoder
import io.getquill.SchemaMeta

final case class Customer(
    id: CustomerId,
    firstName: String,
    lastName: String,
    address: String,
    phone: String,
    email: String,
    passport: Int
):

  def fullName: String = firstName + " " + lastName

object Customer:
  def make(command: CreateCustomer): UIO[Customer] =
    CustomerId.random.map(
      Customer(
        _,
        command.firstName,
        command.lastName,
        command.address,
        command.phone,
        command.email,
        command.passport
      )
    )

  given Schema[Customer]    = DeriveSchema.gen
  given JsonCodec[Customer] = DeriveJsonCodec.gen
