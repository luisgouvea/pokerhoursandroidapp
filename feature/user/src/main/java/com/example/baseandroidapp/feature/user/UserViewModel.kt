package com.example.baseandroidapp.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseandroidapp.core.domain.usecase.UserResult
import com.example.baseandroidapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    userUseCase: UserUseCase,
    private val mapper: UserUIMapper
) : ViewModel() {

val uiUserState: StateFlow<UserUiState> =
    userUseCase.getUser()
        .map { result ->
            when (result) {
                is UserResult.Success -> UserUiState.Success(mapper.toUserUI(result.users))
                is UserResult.Failure -> UserUiState.Error(result.error.message)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserUiState.Loading
        )
}

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val users: List<UserUI>) : UserUiState
    data class Error(val message: String) : UserUiState
}