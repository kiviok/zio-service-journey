package rohan.accounts

import rohan.types.CustomerId

import java.time.LocalDate
import java.util.Date
import java.util.UUID
import zio.Random
import zio.*
import zio.json.DeriveJsonCodec
import zio.json.JsonCodec

final case class Account(
    id: UUID,
    accountType: AccountType,
    dateOpen: LocalDate,
    customerId: CustomerId
)

object Account:
  def make(command: AccountOpen): UIO[Account] = Random.nextUUID.map(
    Account(_, command.accountType, LocalDate.now(), command.customerId)
  )

  given JsonCodec[Account] = DeriveJsonCodec.gen
