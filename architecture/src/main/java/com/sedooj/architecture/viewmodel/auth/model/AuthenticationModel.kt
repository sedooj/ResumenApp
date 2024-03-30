package com.sedooj.architecture.viewmodel.auth.model

import android.content.Context
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
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

    fun validateInput(input: AuthorizationInput): Boolean

    fun auth(
        input: AuthorizationInput,
        usersNetworkRepository: UsersNetworkRepository,
        scope: CoroutineScope,
        context: Context,
    )

}