package com.jaehan.soop.domain.repository

import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun searchRepository(query: String): Flow<ApiResponse<List<Repo>>>
}