package com.sedooj.resumen.viewmodel.models

import android.content.Context
import com.sedooj.resumen.R
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import com.sedooj.resumen.storage.dao.AuthUserDao
import kotlinx.coroutines.CoroutineScope

interface AuthenticationModel {

    enum class AuthState {
        NOT_AUTHORIZED,
        AUTHORIZATION,
        AUTHORIZED
    }

    fun updatePageState(state: AuthState)

    fun resetErrorState()


    fun setError(msg: Int)


    fun validateInput(input: AuthorizationInput): Boolean {
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

    fun coldAuth(
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context
    )

    fun auth(
        input: AuthorizationInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context
    )

}