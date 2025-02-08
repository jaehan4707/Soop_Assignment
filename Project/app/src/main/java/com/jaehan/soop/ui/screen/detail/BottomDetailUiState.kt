package com.jaehan.soop.ui.screen.detail

data class BottomDetailUiState(
    val followers: Long = 0,
    val following: Long = 0,
    val language: List<String> = listOf(),
    val repositoryCount: Long = 0,
    val bio: String = "",
    val userProfileImage: String = "",
    val userName: String = "",
    val isLoading: Boolean = false,
)
