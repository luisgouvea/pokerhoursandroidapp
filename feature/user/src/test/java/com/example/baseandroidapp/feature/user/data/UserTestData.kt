package com.example.baseandroidapp.feature.user.data

import com.example.baseandroidapp.core.model.data.User
import com.example.baseandroidapp.core.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

private val userListDomain = listOf(
    User(id = 1, name = "Leanne Graham"),
    User(id = 2, name = "Ervin Howell"),
    User(id = 3, name = "Clementine Bauch")
)

private val userListNetwork = listOf(
    UserResponse(id = 1, name = "Leanne Graham"),
    UserResponse(id = 2, name = "Ervin Howell"),
    UserResponse(id = 3, name = "Clementine Bauch")
)
val usersListFlow: Flow<List<User>> = flowOf(userListDomain)
val usersNetworkResponse: List<UserResponse> = userListNetwork
val usersList = userListDomain

val firstUserFlow = runBlocking {
    usersListFlow.first()
}
