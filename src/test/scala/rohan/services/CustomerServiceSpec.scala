package rohan.services

import zio.test.*
import zio.*
import rohan.customers.*
import io.getquill.jdbczio.Quill
import io.getquill.SnakeCase
import rohan.customers.CustomerService.create

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
      test("return true for added new customer") {
        for
          customerId <- CustomerService.create(cc)
          inserted   <- CustomerService.getById(customerId)
        yield assertTrue(inserted.get.firstName == cc.firstName)
      },
      test("return true if find customer by passport")(
        for
          cuustomerId        <- CustomerService.create(cc)
          customerByPassport <- CustomerService.getByPassport(cc.passport)
          _ <- Console.printLine(s"${customerByPassport.get.passport} == ${cc.passport}")
        yield assertTrue(customerByPassport.get.passport == cc.passport)
      )
    ).provide(
      CustomerServiceLive.layer,
      Quill.Postgres.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("datasource")
    )
      @@ TestAspect.withLiveRandom
