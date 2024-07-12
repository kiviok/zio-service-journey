package rohan
import io.getquill.MappedEncoding
import zio.*
import zio.schema.*

import java.util.UUID
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

object types:

// UserId type as opaque type and some utils for codecs, etc
// First experience
  opaque type CustomerId = UUID

  object CustomerId:

    def apply(u: UUID): CustomerId            = u
    extension (u: CustomerId) def value: UUID = u

    def random: UIO[CustomerId] = Random.nextUUID

    def fromString(id: String): Task[UUID] = ZIO.attempt(UUID.fromString(id))

    // Schema used for SchemaBasedCodec
    // MappedEncoding instance for Quill codecs
    given Schema[CustomerId]               = Schema.primitive
    given JsonCodec[CustomerId]            = JsonCodec.uuid
    given MappedEncoding[CustomerId, UUID] = MappedEncoding[CustomerId, UUID](_.value)
    given MappedEncoding[UUID, CustomerId] =
      MappedEncoding[UUID, CustomerId](CustomerId(_))
