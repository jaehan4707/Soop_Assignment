package com.jaehan.soop.data.repositoryimpl

import com.jaehan.soop.data.network.api.GithubApi
import com.jaehan.soop.data.network.response.SearchRepositoriesResponse
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
    override suspend fun searchRepository(query: String): Flow<ApiResponse<List<Repo>>> =
        flow {
            val response = safeApiCall(
                apiCall = { api.searchRepositories(query) },
                default = SearchRepositoriesResponse()
            )
            when (response) {
                is ApiResponse.Error -> emit(response)
                is ApiResponse.Success -> emit(
                    ApiResponse.Success(data = response.data.items.map {
                        Repo(
                            id = it.id,
                            userName = it.owner.login,
                            userProfileImage = it.owner.userProfileImage,
                            repositoryName = it.repositoryName ?: "",
                            description = it.description ?: "",
                            starCount = it.starCount ?: 0,
                            language = it.language ?: "",
                        )
                    })
                )
            }
        }
}