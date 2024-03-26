package com.sedooj.resumen.viewmodel

import androidx.lifecycle.ViewModel
import com.sedooj.resumen.R
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.internal.wait

enum class AuthState {
    NOT_AUTHORIZED,
    AUTHORIZATION,
    AUTHORIZED
}

data class AuthUiState(
    var state: AuthState = AuthState.NOT_AUTHORIZED,
)

class AuthorizationViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private fun updatePageState(state: AuthState) {
        _uiState.update {
            it.copy(state = state)
        }
    }

    fun register(
        input: CreateUserInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
    ): Int {
        updatePageState(state = AuthState.AUTHORIZATION)
        if (input.username.isBlank()) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            return R.string.wrong_username_or_password
        }
        if (input.password.isBlank()) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            return R.string.wrong_username_or_password
        }
        if (input.username.length < 6) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            return R.string.wrong_username_length
        }
        if (input.password.length < 8) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            return R.string.wrong_password_length
        }
        var result = 0

        scope.launch {
            val response = usersNetworkRepository.createUser(input = input)
            when (response) {
                -1 -> {
                    updatePageState(state = AuthState.NOT_AUTHORIZED)
                    result =  R.string.no_connection
                }

                200 -> {
                    updatePageState(state = AuthState.AUTHORIZED)
                    result =  0
                }

                400 -> {
                    updatePageState(state = AuthState.NOT_AUTHORIZED)
                    result =  R.string.uncorrect_input_data
                }

                else -> {
                    updatePageState(state = AuthState.NOT_AUTHORIZED)
                    result =  R.string.unknown_error
                }
            }
            return@launch
        }
        return result
    }
}