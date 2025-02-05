package com.jaehan.soop.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jaehan.soop.ui.screen.detail.DetailRoute

fun NavGraphBuilder.detailNavGraph(
    modifier: Modifier,
    navController: NavController
) {
    composable<Route.Detail> { backStackEntry ->
        val owner = backStackEntry.toRoute<Route.Detail>().owner
        val repositoryName = backStackEntry.toRoute<Route.Detail>().repositoryName
        DetailRoute(
            modifier = modifier,
            owner = owner,
            repositoryName = repositoryName,
            onPopUpBackStack = navController::popBackStack
        )
    }
}

fun NavController.navigateToDetail(owner: String, repositoryName: String) {
    navigate(Route.Detail(owner = owner, repositoryName = repositoryName))
}