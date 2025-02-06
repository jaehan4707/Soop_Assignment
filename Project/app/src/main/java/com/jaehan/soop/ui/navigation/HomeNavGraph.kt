package com.jaehan.soop.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jaehan.soop.ui.screen.home.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    modifier: Modifier,
    navController: NavController,
    onShowError: (String) -> Unit,
) {
    composable<Route.Home> {
        HomeRoute(
            modifier = modifier,
            onNavigateToDetail = { owner, repositoryName ->
                navController.navigateToDetail(owner = owner, repositoryName = repositoryName)
            },
            onShowError = { onShowError(it) }
        )
    }
}
