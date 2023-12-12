package com.example.myapplication.serviceapi

class NoNetworkException(error: BaseErrorResponse?,message: String?) : CustomException(error)
class NetworkAuthenticationException(error: BaseErrorResponse?) : CustomException(error)
class NetworkServerException(error: BaseErrorResponse?) : CustomException(error)
class NetworkResourceNotFoundException(error: BaseErrorResponse?) : CustomException(error)
class RequestTimeoutException(error: BaseErrorResponse?) : CustomException(error)
class BadRequestException(error: BaseErrorResponse?) : CustomException(error)
class UnknownException(error: BaseErrorResponse?, message: String?) : CustomException(error)
class NetworkException(error: BaseErrorResponse?) : CustomException(error)
sealed class CustomException(val error: BaseErrorResponse?) : Exception()