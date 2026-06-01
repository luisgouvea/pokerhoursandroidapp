package com.example.baseandroidapp.feature.user.repository

import com.example.baseandroidapp.core.data.repository.UserRepository
import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestUserRepository : UserRepository {

    private val cachedUsers: MutableList<User> = mutableListOf()

    override fun getUser(): Flow<List<User>> = flowOf(
        cachedUsers
    )
}