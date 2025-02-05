package com.jaehan.soop.ui.screen.home

sealed interface HomeUiEvent {
    data class ShowError(val errorMessage: String) : HomeUiEvent
}