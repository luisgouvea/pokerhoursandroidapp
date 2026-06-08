package com.example.baseandroidapp.core.domain.usecase

import com.example.baseandroidapp.core.domain.repository.UserRepository
import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getUser(): Flow<UserResult> =
        userRepository.getUser()
            .map { result ->
                result.fold(
                    onSuccess = { users ->
                        if (users.isEmpty()) UserResult.EmptyList
                        else UserResult.Success(users)
                    },
                    onFailure = { exception ->
                        val domainError = when (exception) {
                            is IOException -> UserError.NetworkUnavailable
                            else -> UserError.Unknown
                        }
                        UserResult.Failure(domainError)
                    }
                )
            }
}

sealed class UserResult {
    data class Success(val users: List<User>) : UserResult()
    data class Failure(val error: UserError) : UserResult()
    object EmptyList : UserResult()
}

sealed class UserError() {
    object NetworkUnavailable : UserError()
    object Unknown : UserError()
}