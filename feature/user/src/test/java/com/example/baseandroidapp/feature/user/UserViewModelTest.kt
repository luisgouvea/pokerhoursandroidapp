package com.example.baseandroidapp.feature.user

import com.example.baseandroidapp.core.domain.UserUseCase
import com.example.baseandroidapp.feature.user.data.usersListFlow
import com.example.baseandroidapp.feature.user.repository.TestUserRepository
import com.example.baseandroidapp.feature.user.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel(
            userUseCase = userUseCase,
        )
    }

    @Test
    fun just_request() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiUserState.collect() }

        userRepository.getUser()
        val result = viewModel.uiUserState.value
        assertIs<UserUiState.Success>(result)

        collectJob.cancel()
    }

        @Test
    fun mockUsersSuccess() = runTest {
            userUseCase = mockk()
            coEvery { userUseCase.getUser() } returns usersListFlow
            viewModel = UserViewModel(userUseCase)

            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiUserState.collect() }

            val result = viewModel.uiUserState.value

            assertIs<UserUiState.Success>(result)
            assertEquals(3, result.users.size)
            assertEquals("Leanne Graham", result.users[0].name)

            collectJob.cancel()
    }
}