package com.jaehan.soop.domain.model

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    sealed class Error(val errorCode: Int? = null, val errorMessage: String = "") :
        ApiResponse<Nothing>() {
        data class ServerError(val code: Int, val message: String) : Error(code, message)
        data class NetworkError(val message: String) : Error(errorMessage = message)
        data class UnknownError(val message: String) : Error(errorMessage = message)
    }
}