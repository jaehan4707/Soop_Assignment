package com.jaehan.soop.domain.model

data class User(
    val userName: String,
    val userProfileImage: String,
    val followers: Long,
    val following: Long,
    val bio: String,
    val repositoryCount: Long,
)
