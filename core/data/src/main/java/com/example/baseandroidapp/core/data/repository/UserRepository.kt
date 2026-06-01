package com.example.baseandroidapp.core.data.repository

import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(
    ): Flow<List<User>>
}