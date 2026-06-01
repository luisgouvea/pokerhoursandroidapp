package com.example.baseandroidapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.baseandroidapp.feature.user.navigation.USER_FEATURE_ROUTE
import com.example.baseandroidapp.feature.user.navigation.userSection
import com.example.baseandroidapp.ui.BaaAppState

@Composable
fun BaaNavHost(
    appState: BaaAppState,
    modifier: Modifier = Modifier,
    startDestination: String = USER_FEATURE_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        userSection()
    }
}
