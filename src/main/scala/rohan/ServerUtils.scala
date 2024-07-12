package rohan

import zio.json.JsonDecoder
import zio.http.Request
import zio.ZIO
import zio.json.*

object ServerUtils:
  def parseBody[A: JsonDecoder](req: Request) =
    for
      body   <- req.body.asString.orElseFail(AppError.MissingBodyError)
      parsed <- ZIO.from(body.fromJson[A])
    yield parsed
