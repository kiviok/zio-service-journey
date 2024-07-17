package rohan.accounts

import neotype.*
import neotype.interop.ziojson.given
import rohan.types.*
import zio.*
import zio.Random
import zio.json.JsonCodec

import java.time.LocalDate
import java.util.UUID

final case class Account(
    id: AccountId,
    accountType: AccountType,
    dateOpen: LocalDate,
    customerId: CustomerId
) derives JsonCodec

object Account:
  def make(command: AccountCreate): UIO[Account] = Random.nextUUID.map(uuid =>
    Account(
      AccountId.unsafeMake(uuid),
      command.accountType,
      LocalDate.now(),
      command.customerId
    )
  )

  // given JsonCodec[Account] = DeriveJsonCodec.gen
