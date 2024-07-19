package rohan.services

import zio.test.*
import zio.*
import rohan.customers.*
import io.getquill.jdbczio.Quill
import io.getquill.SnakeCase
import rohan.FlywayMigration

object CustomerServiceSpec extends ZIOSpecDefault:
  override def spec: Spec[Environment & (TestEnvironment & Scope), Any] =

    val cc = CreateCustomer(
      firstName = "Sara",
      lastName = "Lara",
      address = "Jasum pl.",
      email = "valeron@uo.uo",
      phone = "+85438222",
      passport = 32341342
    )

    suite("CustomerService operations")(
      test("return true for added new customer and findById") {
        for
          customerId <- CustomerService.create(cc)
          inserted   <- CustomerService.getById(customerId)
        yield assertTrue(inserted.get.firstName == cc.firstName)
      },
      test("return true if find customer by passport")(
        for customerByPassport <- CustomerService.getByPassport(cc.passport)
        yield assertTrue(customerByPassport.get.passport == cc.passport)
      ),
      test("return true if getAll customers wokrs")(
        for customers <- CustomerService.getAll
        yield assertTrue(customers.nonEmpty == true)
      ),
      test("return true if update customer")(
        for
          customer <- CustomerService.getByPassport(32341342)
          _ <- CustomerService.update(
            UpdateCustomer(
              customer.get.id,
              firstName = Some(customer.get.firstName + "Updated"),
              None,
              None,
              None,
              None,
              None
            )
          )
          updated <- CustomerService.getById(customer.get.id)
        yield assertTrue(updated.get.firstName.contains("Updated"))
      ),
      test("return true if delete customer")(
        for
          testCustomer <- CustomerService.getByPassport(cc.passport)
          _            <- CustomerService.delete(testCustomer.get.id)
          listWithout  <- CustomerService.getAll
        yield assertTrue(listWithout.contains(testCustomer.get) == false)
      )
    ).provide(
      CustomerServiceLive.layer,
      Quill.Postgres.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("datasource")
    )
      @@ TestAspect.withLiveRandom @@ TestAspect.sequential
