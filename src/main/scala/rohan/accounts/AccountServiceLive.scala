package rohan.accounts

import io.getquill.jdbczio.Quill
import io.getquill.*
import zio.Task
import zio.ZLayer
import rohan.types.*
import neotype.interop.quill.given

final case class AccountServiceLive(quill: Quill.Postgres[SnakeCase])
    extends AccountService:

  import quill.*
  // Schema customization -- table name
  inline implicit def accountSchemaMeta: SchemaMeta[Account] =
    schemaMeta[Account]("accounts")

  override def create(op: AccountCreate): Task[Account] =
    for
      acc <- Account.make(op)
      _   <- run(query[Account].insertValue(lift(acc)))
    yield acc

  override def getAllByCustomerId(c: CustomerId): Task[List[Account]] =
    run(query[Account].filter(_.customerId == lift(c)))

  override def delete(id: AccountId): Task[AccountId] = run(
    query[Account].filter(_.id == lift(id)).delete.returning(_.id)
  )

  override def getAll: Task[List[Account]] = run(query[Account])

  override def getById(id: AccountId): Task[Option[Account]] = run(
    query[Account].filter(_.id == lift(id))
  ).map(_.headOption)

  override def update(au: AccountUpdate): Task[Unit] = run(
    dynamicQuerySchema[Account]("accounts")
      .filter(_.id == lift(au.accountId))
      .update(setOpt(_.accountType, au.accountType))
  ).unit

object AccountServiceLive:
  val layer = ZLayer.derive[AccountServiceLive]
