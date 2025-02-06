package com.jaehan.soop.data.network.response

import com.google.gson.annotations.SerializedName

data class SearchRepositoriesResponse(
    @SerializedName("total_count")
    val count: Int = 0,
    val items: List<SearchResult> = listOf(),
)
