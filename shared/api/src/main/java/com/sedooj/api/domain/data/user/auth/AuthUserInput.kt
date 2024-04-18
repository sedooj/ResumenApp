package com.sedooj.api.domain.data.user.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserInput(
    var username: String = "",
    var password: String = ""
)
