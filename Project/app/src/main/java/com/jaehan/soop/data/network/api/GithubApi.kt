package com.jaehan.soop.data.network.api

import com.jaehan.soop.data.network.response.GetRepositoryInfoResponse
import com.jaehan.soop.data.network.response.SearchRepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): Response<SearchRepositoriesResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryInfo(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
    ): Response<GetRepositoryInfoResponse>
}