package com.example.baseandroidapp.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseandroidapp.core.domain.UserUseCase
import com.example.baseandroidapp.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {

    val uiUserState: StateFlow<UserUiState> =
        userUseCase.getUser()
            .map { UserUiState.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserUiState.Loading,
            )
}

/**
 * A sealed hierarchy describing the state of the feed of news resources.
 */
sealed interface UserUiState {
    /**
     * The feed is still loading.
     */
    object Loading : UserUiState

    /**
     * The feed is loaded with the given list of news resources.
     */
    data class Success(
        /**
         * The list of news resources contained in this feed.
         */
        val users: List<User>
    ) : UserUiState
}