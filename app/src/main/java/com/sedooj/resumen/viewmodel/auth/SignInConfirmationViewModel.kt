package com.sedooj.resumen.viewmodel.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.sedooj.resumen.storage.db.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ConfirmationState {
    APPROVED,
    NOT_SELECTED,
}

data class SignInConfirmationUiState(
    val confirmationState: ConfirmationState = ConfirmationState.NOT_SELECTED,
    val username: String = "",
    val isContentLoaded: Boolean = false,
)

class SignInConfirmationViewModel : ViewModel() {

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

    suspend fun loadData(context: Context) {
        val db = Room.databaseBuilder(
            context = context,
            AppDatabase::class.java, "resumen-app-db"
        ).build()
        val authUserDao = db.authUserDao()
        val authorizationData = authUserDao.getAuthorizationData()
        if (authorizationData != null) {
            updateUsername(authorizationData.username)
            setContentLoaded()
        }
    }
}