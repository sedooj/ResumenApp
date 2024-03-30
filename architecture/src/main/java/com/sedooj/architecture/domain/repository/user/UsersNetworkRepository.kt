package com.sedooj.architecture.domain.repository.user

import com.sedooj.architecture.domain.Client
import com.sedooj.architecture.domain.data.user.create.CreateUserInput
import com.sedooj.architecture.domain.data.user.create.CreateUserOutput
import com.sedooj.architecture.domain.data.user.create.auth.AuthUserInput

interface UsersNetworkRepository : Client {

    suspend fun createUser(input: CreateUserInput): Int

    suspend fun auth(input: AuthUserInput): Int
}