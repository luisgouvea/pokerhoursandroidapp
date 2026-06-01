package com.example.baseandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.baseandroidapp.core.data.util.NetworkMonitor
import com.example.baseandroidapp.ui.BaaApp
import com.example.baseandroidapp.ui.rememberBaaAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val appState = rememberBaaAppState(
                networkMonitor = networkMonitor
            )

            MaterialTheme {
                BaaApp(appState)
            }
        }
    }
}