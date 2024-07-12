package rohan

sealed trait AppError extends Throwable

object AppError:
  case object MissingBodyError extends AppError
