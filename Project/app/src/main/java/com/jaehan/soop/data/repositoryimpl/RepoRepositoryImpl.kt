package com.jaehan.soop.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jaehan.soop.data.network.api.GithubApi
import com.jaehan.soop.data.network.response.GetRepositoryInfoResponse
import com.jaehan.soop.data.network.response.RepoPagingSource
import com.jaehan.soop.data.network.response.mapToRepo
import com.jaehan.soop.data.network.safeApiCall
import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.model.Repo
import com.jaehan.soop.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : RepoRepository {
    override suspend fun searchRepository(query: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepoPagingSource(api, query) }
        ).flow
    }

    override suspend fun getRepositoryInfo(
        owner: String,
        repositoryName: String
    ): Flow<ApiResponse<Repo>> = flow {
        val response = safeApiCall(
            apiCall = {
                api.getRepositoryInfo(owner = owner, repositoryName = repositoryName)
            },
            default = GetRepositoryInfoResponse()
        )
        when (response) {
            is ApiResponse.Error -> emit(response)
            is ApiResponse.Success -> emit(ApiResponse.Success(data = response.data.mapToRepo()))
        }
    }
}
