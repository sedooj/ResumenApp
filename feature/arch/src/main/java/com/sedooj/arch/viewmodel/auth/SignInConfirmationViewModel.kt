package com.sedooj.arch.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.sedooj.localstorage.dao.AuthUserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class ConfirmationState {
    APPROVED,
    NOT_SELECTED,
}

data class SignInConfirmationUiState(
    val confirmationState: ConfirmationState = ConfirmationState.NOT_SELECTED,
    val username: String = "",
    val isContentLoaded: Boolean = false,
)

@HiltViewModel
class SignInConfirmationViewModel @Inject constructor(
    private val authUserDao: AuthUserDao
): ViewModel() {

    private val _uiState = MutableStateFlow(SignInConfirmationUiState())
    val uiState: StateFlow<SignInConfirmationUiState> = _uiState.asStateFlow()

    private fun updateConfirmationState() {
        _uiState.update {
            it.copy(
                confirmationState = ConfirmationState.APPROVED
            )
        }
    }

    private fun setContentLoaded() {
        _uiState.update {
            it.copy(
                isContentLoaded = true
            )
        }
    }

    private fun updateUsername(username: String) {
        _uiState.update {
            it.copy(
                username = username
            )
        }
    }

    fun approve() {
        updateConfirmationState()
    }

    suspend fun loadData() {
        val authorizationData = authUserDao.getAuthorizationData() ?: return
        updateUsername(username = authorizationData.username)
        setContentLoaded()
    }
}