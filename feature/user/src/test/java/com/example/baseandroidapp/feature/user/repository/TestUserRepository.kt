package com.example.baseandroidapp.feature.user.repository

import com.example.baseandroidapp.core.domain.repository.UserRepository
import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestUserRepository : UserRepository {

    private val cachedUsers: MutableList<User> = mutableListOf(
        User(id = 1, name = "Leanne Graham"),
        User(id = 2, name = "Ervin Howell"),
        User(id = 3, name = "Clementine Bauch")
    )

    override fun getUser(): Flow<Result<List<User>>> = flowOf(
        Result.success(cachedUsers)
    )
}