package com.sedooj.architecture.domain.data.user.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserOutput(
    var userId: Long
)