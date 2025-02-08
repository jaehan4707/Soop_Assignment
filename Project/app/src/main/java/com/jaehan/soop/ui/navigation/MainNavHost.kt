package com.jaehan.soop.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val onShowError: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    NavHost(navController, startDestination = Route.Home) {
        homeNavGraph(
            modifier = modifier,
            navController = navController,
            onShowError = onShowError
        )
        detailNavGraph(modifier = modifier, onShowError = { onShowError(it) })
    }
}
