package com.jaehan.soop.ui.screen.detail

data class DetailUiState(
    val userName: String = "",
    val userProfileImage: String = "",
    val repositoryName: String = "",
    val description: String = "",
    val starCount: Long = 0,
    val watchers: Long = 0,
    val forks: Long = 0,
    val topics: List<String> = listOf(),
    val isLoading: Boolean = false,
)
