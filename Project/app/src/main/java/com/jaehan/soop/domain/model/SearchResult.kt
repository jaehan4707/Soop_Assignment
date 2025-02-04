package com.jaehan.soop.domain.model

import kotlinx.serialization.SerialName

data class SearchResult(
    val id: Int,
    @SerialName("owner.avatar_url")
    val userProfileImage: String,
    @SerialName("owner.login")
    val userName: String,
    @SerialName("name")
    val repositoryName: String,
    @SerialName("description")
    val description: String,
    @SerialName("stargazersCount")
    val starCount: Long,
    @SerialName("language")
    val language: String,
)