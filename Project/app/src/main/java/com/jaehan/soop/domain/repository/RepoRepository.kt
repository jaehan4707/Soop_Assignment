package com.jaehan.soop.domain.repository

import androidx.paging.PagingData
import com.jaehan.soop.domain.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun searchRepository(query: String): Flow<PagingData<Repo>>
}