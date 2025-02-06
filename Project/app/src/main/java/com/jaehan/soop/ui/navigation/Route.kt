package com.jaehan.soop.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data class Detail(val owner: String, val repositoryName: String)
}
