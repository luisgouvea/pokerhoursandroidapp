package com.example.baseandroidapp.core.domain.usecase

import com.example.baseandroidapp.core.domain.repository.UserRepository
import com.example.baseandroidapp.core.model.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getUser(): Flow<Result<List<User>>> =
        userRepository.getUser()
            .map { result ->
                result.mapCatching { users ->
                    if (users.isEmpty()) throw UserError.EmptyList
                    users
                }
            }
            .catch { exception ->
                val domainError = when (exception) {
                    is IOException -> UserError.NetworkUnavailable
                    else -> UserError.Unknown(exception.message ?: "Erro")
                }
                emit(Result.failure(domainError))
            }
}

sealed class UserError : Exception() {
    object EmptyList : UserError() {
        override val message = "Nenhum usuário encontrado"
    }
    object NetworkUnavailable : UserError() {
        override val message = "Sem conexão com a internet"
    }
    data class Unknown(override val message: String) : UserError()
}