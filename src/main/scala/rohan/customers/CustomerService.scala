package rohan.customers

import rohan.types.CustomerId
import zio.*
import java.util.UUID

trait CustomerService:

  def create(command: CreateCustomer): Task[CustomerId]
  def getByPassport(passport: Int): Task[Option[Customer]]
  def getById(id: CustomerId): Task[Option[Customer]]
  def getAll: Task[List[Customer]]
  def update(uc: UpdateCustomer): Task[Unit]
  def delete(id: CustomerId): Task[Unit]

object CustomerService:
  def create(command: CreateCustomer): ZIO[CustomerService, Throwable, CustomerId] =
    ZIO.serviceWithZIO[CustomerService](_.create(command))

  def getByPassport(passport: Int): ZIO[CustomerService, Throwable, Option[Customer]] =
    ZIO.serviceWithZIO[CustomerService](_.getByPassport(passport))

  def getById(id: CustomerId): ZIO[CustomerService, Throwable, Option[Customer]] =
    ZIO.serviceWithZIO[CustomerService](_.getById(id))

  def getAll: ZIO[CustomerService, Throwable, List[Customer]] =
    ZIO.serviceWithZIO[CustomerService](_.getAll)

  def update(uc: UpdateCustomer): ZIO[CustomerService, Throwable, Unit] =
    ZIO.serviceWithZIO[CustomerService](_.update(uc))

  def delete(id: CustomerId): ZIO[CustomerService, Throwable, Unit] =
    ZIO.serviceWithZIO[CustomerService](_.delete(id))
