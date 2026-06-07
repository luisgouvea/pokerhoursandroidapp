package com.example.baseandroidapp.feature.user.repository

import com.example.baseandroidapp.core.data.mapper.UserMapper
import com.example.baseandroidapp.core.domain.repository.UserRepository
import com.example.baseandroidapp.core.data.repository.UserRepositoryImpl
import com.example.baseandroidapp.core.network.UserNetworkDataSource
import com.example.baseandroidapp.core.network.model.UserResponse
import com.example.baseandroidapp.feature.user.data.usersList
import com.example.baseandroidapp.feature.user.data.usersNetworkResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private val mockMapper: UserMapper = mockk<UserMapper>()
    private val mockDataSource: UserNetworkDataSource = mockk<UserNetworkDataSource>()

    @Before
    fun setup() {
        repository = UserRepositoryImpl(
            mapper = mockMapper,
            userNetworkDataSourceImpl = mockDataSource,
            ioDispatcher = kotlinx.coroutines.Dispatchers.IO
        )
    }

    @Test
    fun userRepositoryTest_return_first_item() = runTest {
        coEvery { mockDataSource.fetchUser() } returns usersNetworkResponse
        every { mockMapper.toDomain(ofType<List<UserResponse>>()) } returns usersList

        // Act
        val result = repository.getUser().first()

        // Assert
        assertEquals(Result.success(usersList), result)
    }
}