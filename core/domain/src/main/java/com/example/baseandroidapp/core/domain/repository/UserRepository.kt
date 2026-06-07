package com.example.baseandroidapp.core.domain.repository

import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(
    ): Flow<Result<List<User>>>
}