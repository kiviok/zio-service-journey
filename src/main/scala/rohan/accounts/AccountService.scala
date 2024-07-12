package rohan.accounts

import zio.*
import rohan.types.CustomerId

trait AccountService:
  def open(op: AccountOpen): Task[Account]

  def getAccountsForCustomer(c: CustomerId): Task[List[Account]]

object AccountService:
  def open(op: AccountOpen): ZIO[AccountService, Nothing, Task[Account]] =
    ZIO.serviceWith[AccountService](_.open(op))

  def getAccountsForCustomer(
      c: CustomerId
  ): ZIO[AccountService, Nothing, Task[List[Account]]] =
    ZIO.serviceWith[AccountService](_.getAccountsForCustomer(c))
