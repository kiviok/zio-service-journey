package rohan.services

import zio.test.*
import zio.Scope
import zio.test.Spec
import zio.test.TestEnvironment
import rohan.accounts.AccountService
import rohan.customers.CustomerService
import rohan.accounts.AccountCreate
import rohan.accounts.AccountType
import rohan.customers.CustomerServiceLive
import io.getquill.jdbczio.Quill
import io.getquill.SnakeCase
import rohan.accounts.AccountServiceLive
import rohan.accounts.AccountUpdate

object AccountServiceSpec extends ZIOSpecDefault:
  override def spec: Spec[TestEnvironment & Scope, Any] =
    suite("AccountService tests suite")(
      test("return true if create account and find it")(
        for
          customer <- CustomerService.getByPassport(32341342)
          account <- AccountService.create(
            AccountCreate(customer.get.id, AccountType.Fond)
          )
          find <- AccountService.getById(account.id)
        yield assertTrue(find.nonEmpty)
      ),
      test("return true if update account")(
        for
          accounts <- AccountService.getAll
          _ <- AccountService.update(
            AccountUpdate(accounts.head.id, Some(AccountType.Forts))
          )
          updated <- AccountService.getById(accounts.head.id)
        yield assertTrue(updated.get.accountType == AccountType.Forts)
      )
    ).provide(
      CustomerServiceLive.layer,
      AccountServiceLive.layer,
      Quill.Postgres.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("datasource")
    )
      @@ TestAspect.withLiveRandom
