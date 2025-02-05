package com.jaehan.soop.data.network.api

import com.jaehan.soop.data.network.response.SearchRepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): Response<SearchRepositoriesResponse>
}