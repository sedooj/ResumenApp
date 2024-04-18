package com.sedooj.api.domain.repository.user

import com.sedooj.api.domain.Client
import com.sedooj.api.domain.data.user.CreateUserInput
import com.sedooj.api.domain.data.user.auth.AuthUserInput

interface UsersNetworkRepository : Client {

    suspend fun createUser(input: CreateUserInput): Int

    suspend fun auth(input: AuthUserInput): Int
}