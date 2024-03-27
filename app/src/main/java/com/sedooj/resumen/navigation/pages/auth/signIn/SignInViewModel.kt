package com.sedooj.resumen.navigation.pages.auth.signIn

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.sedooj.resumen.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignInUiState(
    val username: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false
)

class SignInViewModel : ViewModel() {
    private var _state = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _state.asStateFlow()

    fun updateUsername(username: String) {
        _state.update {
            it.copy(
                username = username
            )
        }
    }
    fun updatePassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }
    fun updateLogged(authorized: Boolean) {
        _state.update {
            it.copy(isLoggedIn = authorized)
        }
    }
}