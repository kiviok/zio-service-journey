package rohan.accounts

import neotype.interop.ziojson.given
import rohan.types.*
import zio.json.JsonCodec

final case class AccountCreate(customerId: CustomerId, accountType: AccountType)
    derives JsonCodec

final case class AccountUpdate(accountId: AccountId, accountType: Option[AccountType])
    derives JsonCodec
