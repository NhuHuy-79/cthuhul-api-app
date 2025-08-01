package com.nhuhuy.mythos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.nhuhuy.mythos.app.navigation.MythosNavHost
import com.nhuhuy.mythos.core.ui.theme.MythosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MythosTheme {
                val navHostController = rememberNavController()
                MythosNavHost(navHostController = navHostController)
            }
        }
    }
}

