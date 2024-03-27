package com.sedooj.resumen.domain.repository.user

import com.sedooj.resumen.domain.Client
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.data.user.create.CreateUserOutput
import com.sedooj.resumen.domain.data.user.create.auth.AuthUserInput

interface UsersNetworkRepository : Client {

    suspend fun createUser(input: CreateUserInput): Int

    suspend fun auth(input: AuthUserInput): Int
}