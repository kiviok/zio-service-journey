package rohan
import io.getquill.MappedEncoding
import zio.*
import zio.schema.*

import java.util.UUID
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec
import neotype.*

object types:

  type AccountId = AccountId.Type
  object AccountId extends Newtype[UUID]

  type CustomerId = CustomerId.Type
  object CustomerId extends Newtype[UUID]
