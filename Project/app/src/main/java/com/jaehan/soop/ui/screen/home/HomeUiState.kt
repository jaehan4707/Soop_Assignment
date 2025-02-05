package com.jaehan.soop.ui.screen.home

import com.jaehan.soop.domain.model.Repo

data class HomeUiState(
    val isLoading: Boolean = false,
    val results: List<Repo> = listOf(),
    val query: String = "",
)
