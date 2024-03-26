package com.sedooj.resumen.domain.repository.user

import com.sedooj.resumen.domain.Client
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.data.user.create.CreateUserOutput

interface UsersNetworkRepository : Client {

    suspend fun createUser(input: CreateUserInput): Int
}