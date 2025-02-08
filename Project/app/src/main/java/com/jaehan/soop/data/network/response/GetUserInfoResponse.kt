package com.jaehan.soop.data.network.response

import com.google.gson.annotations.SerializedName
import com.jaehan.soop.domain.model.User

data class GetUserInfoResponse(
    val followers: Long? = 0,
    val following: Long? = 0,
    val bio: String? = "",
    @SerializedName("public_repos")
    val repositoryCount: Long? = 0,
    val name: String? = "",
    @SerializedName("avatar_url")
    val userProfileImage: String? = "",
)


fun GetUserInfoResponse.mapToUser() = User(
    following = this.following ?: 0,
    followers = this.followers ?: 0,
    bio = this.bio ?: "",
    repositoryCount = this.repositoryCount ?: 0,
    userName = this.name ?: "",
    userProfileImage = this.userProfileImage ?: "",
    languages = listOf(),
)
