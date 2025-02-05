package com.jaehan.soop.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jaehan.soop.ui.screen.detail.DetailRoute

fun NavGraphBuilder.detailNavGraph(
    modifier: Modifier,
    onShowError: (String) -> Unit,
) {
    composable<Route.Detail> {
        DetailRoute(
            modifier = modifier,
            onShowError = onShowError,
        )
    }
}

fun NavController.navigateToDetail(owner: String, repositoryName: String) {
    navigate(Route.Detail(owner = owner, repositoryName = repositoryName))
}