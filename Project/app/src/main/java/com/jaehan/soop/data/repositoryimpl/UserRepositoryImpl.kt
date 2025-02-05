package com.jaehan.soop.data.repositoryimpl

import com.jaehan.soop.data.network.api.GithubApi
import com.jaehan.soop.data.network.response.GetUserInfoResponse
import com.jaehan.soop.data.network.response.mapToUser
import com.jaehan.soop.data.network.safeApiCall
import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.model.User
import com.jaehan.soop.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
) : UserRepository {
    override suspend fun getUserInfo(userName: String): Flow<ApiResponse<User>> = flow {
        val response = safeApiCall(
            apiCall = { githubApi.getUserInfo(userName) },
            default = GetUserInfoResponse()
        )
        when (response) {
            is ApiResponse.Error -> {
                emit(response)
            }

            is ApiResponse.Success -> {
                emit(ApiResponse.Success(data = response.data.mapToUser()))
            }
        }
    }
}