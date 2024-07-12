package rohan.accounts

import io.getquill.MappedEncoding
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec
import zio.schema.Schema
import zio.schema.DeriveSchema
import zio.json.JsonEncoder

enum AccountType:
  case Fond
  case Forts
  case Fx

object AccountType:

  def fromString(s: String) = s.toLowerCase() match
    case "fond"  => Fond
    case "forts" => Forts
    case "fx"    => Fx

  given MappedEncoding[AccountType, String] =
    MappedEncoding[AccountType, String](_.toString)
  given MappedEncoding[String, AccountType] =
    MappedEncoding[String, AccountType](AccountType.fromString(_))

  given JsonCodec[AccountType] = DeriveJsonCodec.gen
