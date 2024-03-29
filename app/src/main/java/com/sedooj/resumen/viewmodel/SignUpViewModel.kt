package com.sedooj.resumen.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.sedooj.resumen.R
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import com.sedooj.resumen.storage.dao.AuthUserDao
import com.sedooj.resumen.storage.db.AppDatabase
import com.sedooj.resumen.storage.entity.AuthUserEntity
import com.sedooj.resumen.viewmodel.models.AuthenticationModel
import com.sedooj.resumen.viewmodel.models.AuthorizationInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import com.sedooj.resumen.viewmodel.models.AuthenticationModel.AuthState


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

    override fun coldAuth(
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context,
    ) {
        TODO("Not yet implemented")
    }

    override fun auth(
        input: AuthorizationInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context
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
                        val db = Room.databaseBuilder(
                            context = context,
                            AppDatabase::class.java, "resumen-app-db"
                        ).build()

                        db.authUserDao().update(
                            auth = AuthUserEntity(
                                id = 1,
                                username = input.username,
                                password = input.password
                            )
                        )
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