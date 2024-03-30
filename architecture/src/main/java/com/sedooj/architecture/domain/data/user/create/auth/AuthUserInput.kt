package com.sedooj.architecture.domain.data.user.create.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserInput(
    var username: String = "",
    var password: String = ""
)
