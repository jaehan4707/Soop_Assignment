package com.jaehan.soop.data.network.response

import kotlinx.serialization.SerialName

data class SearchRepositoriesResponse(
    @SerialName("total_count")
    val count: Int = 0,
    val items: List<SearchResult> = listOf(),
)
