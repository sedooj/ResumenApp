package com.sedooj.resumen.viewmodel

import androidx.lifecycle.ViewModel

private enum class AuthState {
    NOT_AUTHORIZED,
    AUTHORIZATION,
    AUTHORIZED,
    NONE
}

class AuthorizationViewModel(): ViewModel() {


}