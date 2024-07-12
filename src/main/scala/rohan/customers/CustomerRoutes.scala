package rohan.customers

import rohan.types.CustomerId
import zio.ZIO
import zio.ZLayer
import zio.http.*
import zio.json.*
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec

import java.util.UUID
import rohan.ServerUtils

final case class CustomerRoutes(customers: CustomerService):

  val all: Routes[Any, Response] = Routes(
    Method.GET / "customer" / zio.http.uuid("customerId") -> handler {
      (customerId: UUID, _: Request) =>
        for customer <- customers.getById(CustomerId(customerId))
        yield Response.json(customer.toJson)
    },
    Method.POST / "customer" / "new" ->
      handler { (req: Request) =>
        for
          cu <- ServerUtils.parseBody[CreateCustomer](req)
          id <- customers.create(cu)
        yield Response.text(id.toString())
      }
  ).handleError(e => Response.internalServerError(e.toString()))

object CustomerRoutes:
  val layer = ZLayer.fromFunction(CustomerRoutes.apply)
