package rohan.accounts

import zio.*
import rohan.types.CustomerId
import rohan.types.AccountId

trait AccountService:
  def create(op: AccountCreate): Task[Account]
  def getById(id: AccountId): Task[Option[Account]]
  def getAllByCustomerId(c: CustomerId): Task[List[Account]]
  def getAll: Task[List[Account]]
  def update(au: AccountUpdate): Task[Unit]
  def delete(id: AccountId): Task[AccountId]

object AccountService:
  def create(op: AccountCreate): ZIO[AccountService, Throwable, Account] =
    ZIO.serviceWithZIO[AccountService](_.create(op))

  def getById(id: AccountId): ZIO[AccountService, Throwable, Option[Account]] =
    ZIO.serviceWithZIO[AccountService](_.getById(id))

  def getAllByCustomerId(
      c: CustomerId
  ): ZIO[AccountService, Throwable, List[Account]] =
    ZIO.serviceWithZIO[AccountService](_.getAllByCustomerId(c))

  def getAll: ZIO[AccountService, Throwable, List[Account]] =
    ZIO.serviceWithZIO[AccountService](_.getAll)

  def update(au: AccountUpdate): ZIO[AccountService, Throwable, Unit] =
    ZIO.serviceWithZIO[AccountService](_.update(au))

  def delete(id: AccountId): ZIO[AccountService, Throwable, AccountId] =
    ZIO.serviceWithZIO[AccountService](_.delete(id))
