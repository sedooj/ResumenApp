package com.sedooj.api.domain.data.user.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserOutput(
    var userId: Long
)