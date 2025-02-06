package com.jaehan.soop.data.network.response

import com.google.gson.annotations.SerializedName
import com.jaehan.soop.domain.model.Repo

data class SearchResult(
    val id: Int = 0,
    val owner: Owner = Owner(),
    @SerializedName("name")
    val repositoryName: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("stargazers_count")
    val starCount: Long? = 0,
    @SerializedName("language")
    val language: String? = "",
)

fun List<SearchResult>.mapToRepo() = this.map {
    Repo(
        id = it.id,
        userName = it.owner.login,
        userProfileImage = it.owner.userProfileImage,
        repositoryName = it.repositoryName ?: "",
        description = it.description ?: "",
        starCount = it.starCount ?: 0,
        language = it.language ?: "",
    )
}