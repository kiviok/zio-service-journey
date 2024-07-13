package rohan.services

import zio.test.*
import zio.*
import rohan.customers.*
import io.getquill.jdbczio.Quill
import io.getquill.SnakeCase

object CustomerServiceSpec extends ZIOSpecDefault:
  override def spec: Spec[Environment & (TestEnvironment & Scope), Any] =
    suite("Add new customer in DB")(
      test("return true for added new customer") {
        val cc = CreateCustomer(
          firstName = "Sara",
          lastName = "Lara",
          address = "Jasum pl.",
          email = "valeron@uo.uo",
          phone = "+85438222",
          passport = 32341342
        )
        for
          customerId <- CustomerService.create(cc)
          inserted   <- CustomerService.getById(customerId)
        yield assertTrue(inserted.get.firstName == "Sara")
      }.provide(
        CustomerServiceLive.layer,
        Quill.Postgres.fromNamingStrategy(SnakeCase),
        Quill.DataSource.fromPrefix("datasource")
      )
    ) @@ TestAspect.withLiveRandom
