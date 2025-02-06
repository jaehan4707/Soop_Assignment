package com.jaehan.soop.data.network.response

import com.google.gson.annotations.SerializedName
import com.jaehan.soop.domain.model.Repo

data class GetRepositoryInfoResponse(
    val owner: Owner = Owner(),
    @SerializedName("stargazers_count")
    val star: Long? = 0L,
    val watchers: Long? = 0L,
    val forks: Long? = 0L,
    val description: String? = "",
    val topics: List<String>? = null,
    val name: String? = null,
    val language: String? = null,
)


fun GetRepositoryInfoResponse.mapToRepo(): Repo {
    return Repo(
        userName = this.owner.login,
        userProfileImage = this.owner.userProfileImage,
        starCount = this.star ?: 0,
        watchers = this.watchers ?: 0,
        forks = this.forks ?: 0,
        topics = this.topics ?: listOf(),
        description = this.description ?: "",
        repositoryName = this.name ?: ""
    )
}
