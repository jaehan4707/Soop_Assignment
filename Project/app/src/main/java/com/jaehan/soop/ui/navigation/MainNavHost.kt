package com.jaehan.soop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Route.Home) {
        homeNavGraph(modifier = modifier, navController = navController)
        detailNavGraph(modifier = modifier, navController = navController)
    }
}
