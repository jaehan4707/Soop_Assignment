package com.jaehan.soop.domain.repository

import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserInfo(userName: String): Flow<ApiResponse<User>>
    suspend fun getUserRepositories(userName: String): Flow<ApiResponse<List<String>>>
}
