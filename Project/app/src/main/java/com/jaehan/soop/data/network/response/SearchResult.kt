package com.jaehan.soop.data.network.response

import com.google.gson.annotations.SerializedName

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