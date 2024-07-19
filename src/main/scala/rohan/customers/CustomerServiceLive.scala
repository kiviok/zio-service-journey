package rohan.customers

import zio.Task

import zio.ZLayer
import io.getquill.jdbczio.Quill
import io.getquill.*
import java.util.UUID
import rohan.types.*
import io.getquill.jdbczio.Quill.Postgres
import neotype.interop.quill.given

case class CustomerServiceLive(quill: Quill.Postgres[SnakeCase]) extends CustomerService:

  import quill.*
  // Schema customization -- table name
  inline implicit def customerSchemaMeta: SchemaMeta[Customer] =
    schemaMeta[Customer]("customers")

  override def create(command: CreateCustomer): Task[CustomerId] =
    for
      customer   <- Customer.make(command)
      customerId <- run(query[Customer].insertValue(lift(customer)).returning(_.id))
    yield customerId

  override def getByPassport(passport: Int): Task[Option[Customer]] =
    run(query[Customer].filter(_.passport == lift(passport))).map(_.headOption)

  override def getById(id: CustomerId): Task[Option[Customer]] =
    run(query[Customer].filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Task[List[Customer]] = run(query[Customer])

  override def delete(id: CustomerId): Task[Unit] =
    run(query[Customer].filter(_.id == lift(id)).delete).unit

  override def update(uc: UpdateCustomer) = run(
    dynamicQuerySchema[Customer]("customers")
      .filter(_.id == lift(uc.id))
      .update(
        setOpt(_.firstName, uc.firstName),
        setOpt(_.lastName, uc.lastName),
        setOpt(_.address, uc.address),
        setOpt(_.email, uc.email),
        setOpt(_.phone, uc.phone),
        setOpt(_.passport, uc.passport)
      )
  ).unit

object CustomerServiceLive:
  val layer: ZLayer[Postgres[SnakeCase], Nothing, CustomerServiceLive] =
    ZLayer.derive[CustomerServiceLive]
