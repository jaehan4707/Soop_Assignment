package com.jaehan.soop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.jaehan.soop.ui.navigation.MainNavHost
import com.jaehan.soop.ui.theme.SOOP_Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SOOP_Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
