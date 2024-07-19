package rohan.customers

import rohan.types.CustomerId
import zio.*
import java.util.UUID

trait CustomerService:

  def create(command: CreateCustomer): Task[CustomerId]
  def getByPassport(passport: Int): Task[Option[Customer]]
  def getById(id: CustomerId): Task[Option[Customer]]
  def update(uc: UpdateCustomer): Task[Unit]
  def delete(id: CustomerId): Task[Unit]
