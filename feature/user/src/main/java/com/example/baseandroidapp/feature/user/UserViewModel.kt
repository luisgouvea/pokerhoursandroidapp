package com.example.baseandroidapp.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseandroidapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    userUseCase: UserUseCase,
    private val mapper: UserUIMapper
) : ViewModel() {

    val uiUserState: StateFlow<UserUiState> = userUseCase.getUser()
        .map { domainUsers ->
            runCatching { UserUiState.Success(mapper.toUserUI(domainUsers)) }
                .getOrElse { UserUiState.Error(it.message ?: "Error occurred") }
        }
        .catch { emit(UserUiState.Error(it.message ?: "API error")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UserUiState.Loading)
}

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val users: List<UserUI>) : UserUiState
    data class Error(val message: String) : UserUiState
}