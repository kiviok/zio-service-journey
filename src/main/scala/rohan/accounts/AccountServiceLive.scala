package rohan.accounts

import io.getquill.jdbczio.Quill
import io.getquill.*
import zio.Task
import zio.ZLayer
import rohan.types.CustomerId
import rohan.customers.Customer

final case class AccountServiceLive(quill: Quill.Postgres[SnakeCase])
    extends AccountService:

  import quill.*
  // Schema customization -- table name
  inline implicit def accountSchemaMeta: SchemaMeta[Account] =
    schemaMeta[Account]("accounts")

  override def open(op: AccountOpen): Task[Account] =
    for
      acc <- Account.make(op)
      _   <- run(query[Account].insertValue(lift(acc)))
    yield acc

  override def getAccountsForCustomer(c: CustomerId): Task[List[Account]] =
    run(query[Account].filter(_.customerId == lift(c)))

object AccountServiceLive:
  val layer = ZLayer.derive[AccountServiceLive]
