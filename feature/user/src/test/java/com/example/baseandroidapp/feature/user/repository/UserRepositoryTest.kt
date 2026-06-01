package com.example.baseandroidapp.feature.user.repository

import com.example.baseandroidapp.core.data.mapper.UserMapper
import com.example.baseandroidapp.core.data.repository.UserRepository
import com.example.baseandroidapp.core.data.repository.UserRepositoryImpl
import com.example.baseandroidapp.core.network.BaaNetworkDataSource
import com.example.baseandroidapp.core.network.model.UserResponse
import com.example.baseandroidapp.feature.user.data.firstUserFlow
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
    private val mockDataSource: BaaNetworkDataSource = mockk<BaaNetworkDataSource>()

    @Before
    fun setup() {
        repository = UserRepositoryImpl(
            mapper = mockMapper,
            baaNetworkDataSourceImpl = mockDataSource
        )
    }

    @Test
    fun userRepositoryTest_return_first_item() = runTest {
        coEvery { mockDataSource.fetchUser() } returns usersNetworkResponse
        every { mockMapper.toDomain(ofType<List<UserResponse>>()) } returns usersList

        // Act
        val result = repository.getUser().first()

        // Assert
        assertEquals(firstUserFlow, result)
    }
}