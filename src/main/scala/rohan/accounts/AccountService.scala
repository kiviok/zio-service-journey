package rohan.accounts

import zio.*
import rohan.types.CustomerId
import rohan.types.AccountId

trait AccountService:
  def create(op: AccountCreate): Task[Account]
  def getById(id: AccountId): Task[Option[Account]]
  def getAllByCustomerId(c: CustomerId): Task[List[Account]]
  def getAll: Task[List[Account]]
  def delete(id: AccountId): Task[AccountId]
  def update(au: AccountUpdate): Task[Unit]
