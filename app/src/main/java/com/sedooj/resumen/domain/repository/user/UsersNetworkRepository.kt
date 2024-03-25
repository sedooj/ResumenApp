package com.sedooj.resumen.domain.repository.user

import com.sedooj.resumen.domain.Client
import com.sedooj.resumen.domain.data.user.create.CreateUserInput

interface UsersNetworkRepository : Client {

    suspend fun createUser(input: CreateUserInput)
}