package com.sedooj.resumen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.sedooj.resumen.R
import com.sedooj.resumen.domain.data.user.create.auth.AuthUserInput
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import com.sedooj.resumen.storage.db.AppDatabase
import com.sedooj.resumen.storage.entity.AuthUserEntity
import com.sedooj.resumen.viewmodel.models.AuthenticationModel
import com.sedooj.resumen.viewmodel.models.AuthenticationModel.AuthState
import com.sedooj.resumen.viewmodel.models.AuthorizationInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException

data class SignInUiState(
    val state: AuthState = AuthState.NOT_AUTHORIZED,
    val error: Int? = null,
)

class SignInViewModel : ViewModel(), AuthenticationModel {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()
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

    override fun coldAuth(
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context,
    ) {
        updatePageState(state = AuthState.AUTHORIZATION)
        scope.launch {
            try {
                doRequest(
                    usersNetworkRepository = usersNetworkRepository,
                    scope = this,
                    context = context
                )
            } catch (e: ConnectException) {
                setError(R.string.no_connection)
                updatePageState(state = AuthState.NOT_AUTHORIZED)
            }
            return@launch
        }
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
                doRequest(
                    input = input,
                    usersNetworkRepository = usersNetworkRepository,
                    scope = this,
                    context = context
                )
            } catch (e: ConnectException) {
                setError(R.string.no_connection)
                updatePageState(state = AuthState.NOT_AUTHORIZED)
            }
            return@launch
        }
    }

    private suspend fun doRequest(
        input: AuthorizationInput? = null,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context,
    ) {
        val db = Room.databaseBuilder(
            context = context,
            AppDatabase::class.java, "resumen-app-db"
        ).build()
        var authData: AuthorizationInput? = input
        if (authData == null) {
            val authorizationData: AuthUserEntity? = db.authUserDao().getAuthorizationData()
            if (authorizationData != null)
                authData = AuthorizationInput(
                    username = authorizationData.username,
                    password = authorizationData.password
                )
            else {
                updatePageState(state = AuthState.NOT_AUTHORIZED)
                return
            }
            val response = usersNetworkRepository.auth(
                input = AuthUserInput(
                    username = authData.username,
                    password = authData.password
                )
            )
            return when (response) {
                404 -> {
                    setError(R.string.user_not_found)
                    updatePageState(state = AuthState.NOT_AUTHORIZED)
                }

                403 -> {
                    setError(R.string.wrong_username_or_password)
                    updatePageState(state = AuthState.NOT_AUTHORIZED)
                }

                200 -> {
                    db.authUserDao().update(
                        auth = AuthUserEntity(
                            id = 1,
                            username = authData.username,
                            password = authData.password
                        )
                    )

                    resetErrorState()
                    updatePageState(state = AuthState.AUTHORIZED)
                }

                else -> {
                    setError(R.string.unknown_error)
                    updatePageState(state = AuthState.NOT_AUTHORIZED)
                }
            }

        }
    }
}