package com.example.baseandroidapp.feature.user

import com.example.baseandroidapp.core.domain.usecase.UserUseCase

import com.example.baseandroidapp.feature.user.repository.TestUserRepository
import com.example.baseandroidapp.feature.user.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class UserViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userRepository = TestUserRepository()

    private var userUseCase = UserUseCase(
        userRepository = userRepository
    )
    private var userUIMapper = UserUIMapper()
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel(
            userUseCase = userUseCase,
            mapper = userUIMapper
        )
    }

    @Test
    fun just_request() = runTest {
        val result = viewModel.uiUserState.first { it is UserUiState.Success }
        assertIs<UserUiState.Success>(result)
    }

        @Test
    fun mockUsersSuccess() = runTest {
            userUseCase = mockk()
            val mockFlow = kotlinx.coroutines.flow.flowOf(
                Result.success(
                    listOf(
                        com.example.baseandroidapp.core.model.data.User(1, "Leanne Graham"),
                        com.example.baseandroidapp.core.model.data.User(2, "Ervin Howell"),
                        com.example.baseandroidapp.core.model.data.User(3, "Clementine Bauch")
                    )
                )
            )
            coEvery { userUseCase.getUser() } returns mockFlow
            viewModel = UserViewModel(userUseCase, userUIMapper)

            val result = viewModel.uiUserState.first { it is UserUiState.Success }

            assertIs<UserUiState.Success>(result)
            assertEquals(3, result.users.size)
            assertEquals("Leanne Graham", result.users[0].name)
    }
}