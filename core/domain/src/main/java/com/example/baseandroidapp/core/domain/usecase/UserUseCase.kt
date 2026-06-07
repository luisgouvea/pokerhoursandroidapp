package com.example.baseandroidapp.core.domain.usecase

import com.example.baseandroidapp.core.domain.repository.UserRepository
import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {
    fun getUser(): Flow<List<User>> =
        userRepository.getUser()
}