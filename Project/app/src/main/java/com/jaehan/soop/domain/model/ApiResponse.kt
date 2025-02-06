package com.jaehan.soop.domain.model

import com.jaehan.soop.data.network.error.ErrorMessages

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()

    sealed class Error(val errorCode: Int? = null, val errorMessage: String = "") :
        ApiResponse<Nothing>() {
        data class ServerError(
            val code: Int,
            val message: String = ErrorMessages.SERVER_ERROR_MESSAGE
        ) : Error(code, message)

        data class ForbiddenError(val message: String = ErrorMessages.FORBIDDEN_ERROR_MESSAGE) :
            Error(errorCode = 403, errorMessage = message)

        data class TooManyRequestError(val message: String = ErrorMessages.TOO_MANY_REQUESTS_ERROR_MESSAGE) :
            Error(errorCode = 429, errorMessage = message)

        data class UnknownError(val message: String = ErrorMessages.UNKNOWN_ERROR_MESSAGE) :
            Error(errorMessage = message)

        companion object {
            fun fromCode(code: Int, message: String = ""): Error {
                return when (code) {
                    403 -> ForbiddenError()
                    429 -> TooManyRequestError()
                    else -> ServerError(code, message)
                }
            }
        }
    }
}
