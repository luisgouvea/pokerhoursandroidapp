package com.example.baseandroidapp.feature.user.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseandroidapp.feature.user.DetailsUserRoute
import com.example.baseandroidapp.feature.user.UserRoute

const val USER_FEATURE_ROUTE = "USER_FEATURE"

fun NavController.navigateToUser(navOptions: NavOptions) = navigate(USER_FEATURE_ROUTE, navOptions)

fun NavGraphBuilder.userSection() {
    composable(
        route = USER_FEATURE_ROUTE
    ) {
        UserNavHost()
    }
}

@Composable
fun UserNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = Screen.Users.route) {
        composable(Screen.Users.route) {
            UserRoute(
                onNavigateToDetails = { userId ->
                    navController.navigate(Screen.DetailsUser.paramsDetailUser(userId))
                }
            )
        }

        composable(Screen.DetailsUser.route) { backStackEntry ->
            val id: String? = backStackEntry.arguments?.getString("id")
            id?.let {
                DetailsUserRoute(userId = it.toInt())
            }
        }
    }
}

object Routes {
    const val USER_ROUTE = "user_route"
    const val DETAILS_USER_ROUTE = "details_user_route/{id}"
    fun setArgumentsDetailsUser(userId: Int) = "details_user_route/$userId"
}

sealed class Screen(val route: String) {
    object Users : Screen(route = Routes.USER_ROUTE)

    object DetailsUser : Screen(route = Routes.DETAILS_USER_ROUTE) {
        fun paramsDetailUser(userId: Int) = Routes.setArgumentsDetailsUser(userId)
    }
}