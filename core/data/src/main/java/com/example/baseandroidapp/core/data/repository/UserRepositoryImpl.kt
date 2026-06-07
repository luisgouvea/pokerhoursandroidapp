package com.example.baseandroidapp.core.data.repository

import com.example.baseandroidapp.core.common.di.IoDispatcher
import com.example.baseandroidapp.core.data.mapper.UserMapper
import com.example.baseandroidapp.core.domain.repository.UserRepository
import com.example.baseandroidapp.core.model.data.User
import com.example.baseandroidapp.core.network.UserNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userNetworkDataSourceImpl: UserNetworkDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override fun getUser(): Flow<Result<List<User>>> = flow {
        emit(
            runCatching {
                mapper.toDomain(userNetworkDataSourceImpl.fetchUser())
            }
        )
    }.flowOn(ioDispatcher)
}