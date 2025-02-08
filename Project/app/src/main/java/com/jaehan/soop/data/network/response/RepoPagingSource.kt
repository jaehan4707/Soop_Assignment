package com.jaehan.soop.data.network.response

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jaehan.soop.data.network.api.GithubApi
import com.jaehan.soop.data.network.safeApiCall
import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.model.Repo

class RepoPagingSource(
    private val api: GithubApi,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val currentPage = params.key ?: 1
        val response = safeApiCall(
            apiCall = { api.searchRepositories(query, currentPage, params.loadSize) },
            default = SearchRepositoriesResponse()
        )
        return when (response) {
            is ApiResponse.Success -> {
                LoadResult.Page(
                    data = response.data.items.mapToRepo(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (response.data.items.isEmpty()) null else currentPage + 1
                )
            }

            is ApiResponse.Error -> {
                LoadResult.Error(Exception(response.errorMessage))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
