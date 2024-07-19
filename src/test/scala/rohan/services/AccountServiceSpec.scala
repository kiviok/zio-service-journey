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
import rohan.customers.CreateCustomer

object AccountServiceSpec extends ZIOSpecDefault:
  override def spec: Spec[TestEnvironment & Scope, Any] =

    val cc = CreateCustomer(
      firstName = "John",
      lastName = "Doe",
      address = "Barsum pl.",
      email = "joii@uo.uo",
      phone = "+23222",
      passport = 121212
    )

    suite("AccountService tests suite")(
      test("return true if create account and find it")(
        for
          _        <- CustomerService.create(cc)
          customer <- CustomerService.getByPassport(cc.passport)
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
      ),
      test("return true if delete account successfully")(
        for
          list        <- AccountService.getAll
          _           <- AccountService.delete(list.head.id)
          listWithout <- AccountService.getAll
        yield assertTrue(listWithout.contains(list.head) == false)
      )
    ).provide(
      CustomerServiceLive.layer,
      AccountServiceLive.layer,
      Quill.Postgres.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("datasource")
    )
      @@ TestAspect.withLiveRandom @@ TestAspect.sequential
