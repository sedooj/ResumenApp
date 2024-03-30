package com.sedooj.architecture.viewmodel.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sedooj.architecture.R
import com.sedooj.architecture.domain.repository.user.UsersNetworkRepository
import com.sedooj.architecture.viewmodel.auth.model.AuthenticationModel
import com.sedooj.architecture.viewmodel.auth.model.AuthenticationModel.*
import com.sedooj.architecture.viewmodel.auth.model.AuthorizationInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException

data class SignUpUiState(
    val state: AuthState = AuthState.NOT_AUTHORIZED,
    val error: Int? = null,
)

class SignUpViewModel : ViewModel(), AuthenticationModel {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    override fun updatePageState(state: AuthState) {
        _uiState.update {
            it.copy(state = state)
        }
    }

    override fun resetErrorState() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    override fun setError(msg: Int) {
        _uiState.update {
            it.copy(
                error = msg
            )
        }
    }

    override fun validateInput(input: AuthorizationInput): Boolean {
        if (input.username.isBlank()) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_username_or_password)
            return false
        }
        if (input.password.isBlank()) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_username_or_password)
            return false
        }
        if (input.username.length < 6) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_username_length)
            return false
        }
        if (input.password.length < 8) {
            updatePageState(state = AuthState.NOT_AUTHORIZED)
            setError(R.string.wrong_password_length)
            return false
        }
        return true
    }

    override fun auth(
        input: AuthorizationInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context,
    ) {
        updatePageState(state = AuthState.AUTHORIZATION)
        if (!validateInput(
                input = input
            )
        ) return
        scope.launch {
            try {
                val response = usersNetworkRepository.createUser(
                    input = CreateUserInput(
                        username = input.username,
                        password = input.password
                    )
                )
                when (response) {
                    200 -> {
                        resetErrorState()
                        updatePageState(state = AuthState.AUTHORIZED)
                    }

                    400 -> {
                        setError(R.string.uncorrect_input_data)
                        updatePageState(state = AuthState.NOT_AUTHORIZED)
                    }

                    409 -> {
                        setError(R.string.user_already_exist)
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