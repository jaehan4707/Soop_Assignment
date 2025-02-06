package com.jaehan.soop.ui.screen.detail

sealed interface DetailUiEvent {
    data class ShowError(val errorMessage: String) : DetailUiEvent
}
