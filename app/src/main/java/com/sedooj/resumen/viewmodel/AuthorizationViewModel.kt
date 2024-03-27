package com.sedooj.resumen.viewmodel

import androidx.lifecycle.ViewModel
import com.sedooj.resumen.R
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException

enum class AuthState {
    NOT_AUTHORIZED,
    AUTHORIZATION,
    AUTHORIZED
}

data class AuthUiState(
    val state: AuthState = AuthState.NOT_AUTHORIZED,
    val error: Int? = null,
)

class AuthorizationViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private fun updatePageState(state: AuthState) {
        _uiState.update {
            it.copy(state = state)
        }
    }

    fun resetErrorState() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    private fun setError(msg: Int) {
        _uiState.update {
            it.copy(
                error = msg
            )
        }
    }

    private fun validateInput(input: CreateUserInput) {
        if (input.username.isBlank()) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_username_or_password)
            return
        }
        if (input.password.isBlank()) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_username_or_password)
            return
        }
        if (input.username.length < 6) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_username_length)
            return
        }
        if (input.password.length < 8) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_password_length)
            return
        }
    }

    fun login(
        input: CreateUserInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
    ) {
        updatePageState(state = AuthState.AUTHORIZATION)
        validateInput(input = input)
        scope.launch {

        }
    }

    fun register(
        input: CreateUserInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
    ) {
        updatePageState(state = AuthState.AUTHORIZATION)
        validateInput(input = input)
        scope.launch {
            try {
                val response = usersNetworkRepository.createUser(input = input)
                when (response) {
                    200 -> {
                        resetErrorState()
                        updatePageState(state = AuthState.AUTHORIZED)
                    }

                    400 -> {
                        setError(R.string.uncorrect_input_data)
                        updatePageState(state = AuthState.NOT_AUTHORIZED)
                    }

                    else -> {
                        setError(R.string.unknown_error)
                        updatePageState(state = AuthState.NOT_AUTHORIZED)
                    }
                }
            } catch (e: ConnectException) {
                setError(R.string.no_connection)
                updatePageState(state = AuthState.NOT_AUTHORIZED)
            }
            return@launch
        }
    }
}