package com.jaehan.soop.data.network

import com.jaehan.soop.domain.model.ApiResponse
import retrofit2.Response


suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>,
    default: T,
): ApiResponse<T> {
    return runCatching {
        val response = apiCall()
        if (response.isSuccessful) {
            ApiResponse.Success(data = response.body() ?: default)
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            if (errorMessage == "Unknown error") {
                ApiResponse.Error.UnknownError(errorMessage)
            } else {
                ApiResponse.Error.ServerError(response.code(), errorMessage)
            }
        }
    }.getOrElse {
        ApiResponse.Error.NetworkError(it.message ?: "Network error")
    }
}