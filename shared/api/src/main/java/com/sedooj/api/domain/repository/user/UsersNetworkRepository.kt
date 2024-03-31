package com.sedooj.api.domain.repository.user

import com.sedooj.api.domain.Client
import com.sedooj.api.domain.data.user.create.CreateUserInput
import com.sedooj.api.domain.data.user.create.auth.AuthUserInput

interface UsersNetworkRepository : Client {

    suspend fun createUser(input: CreateUserInput): Int

    suspend fun auth(input: AuthUserInput): Int
}