package com.jaehan.soop.domain.model

data class Repo(
    val id: Int = 0,
    val userName: String = "",
    val userProfileImage: String = "",
    val repositoryName: String = "",
    val description: String = "",
    val starCount: Long = 0,
    val language: String = "",
    val watchers: Long = 0,
    val forks: Long = 0,
    val topics: List<String> = listOf(),
)
