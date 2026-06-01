package com.example.baseandroidapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.baseandroidapp.core.designsystem.icon.BaaIcons
import com.example.baseandroidapp.user.R as userR
/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    USER_FEATURE(
        selectedIcon = BaaIcons.Person,
        unselectedIcon = BaaIcons.PersonBorder,
        iconTextId =  userR.string.feature_user_title,
        titleTextId = userR.string.feature_user_title,
    )
}