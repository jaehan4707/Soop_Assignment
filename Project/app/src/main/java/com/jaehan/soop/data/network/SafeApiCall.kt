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
            ApiResponse.Error.fromCode(response.code())
        }
    }.getOrElse {
        ApiResponse.Error.UnknownError()
    }
}
