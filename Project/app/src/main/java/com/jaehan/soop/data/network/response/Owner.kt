package com.jaehan.soop.data.network.response

import com.google.gson.annotations.SerializedName

data class Owner(
    val login: String = "",
    @SerializedName("avatar_url")
    val userProfileImage: String = "",
)
