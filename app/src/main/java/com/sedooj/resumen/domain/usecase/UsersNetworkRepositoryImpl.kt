package com.sedooj.resumen.domain.usecase

import android.provider.VoicemailContract
import com.sedooj.resumen.domain.NetworkConfig
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.data.user.create.CreateUserOutput
import com.sedooj.resumen.domain.data.user.create.auth.AuthUserInput
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UsersNetworkRepositoryImpl(
    private val client: HttpClient
) : UsersNetworkRepository {
    override suspend fun auth(input: AuthUserInput): Boolean {
        val response = client.get("${NetworkConfig.BASE_URL}sign-in") {

        }
    }

    override suspend fun createUser(input: CreateUserInput): Int {
        val response = client.post("${NetworkConfig.AUTH_URL}sign-up") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("username", value = input.username)
                    append("password", value = input.password)
                }
            ))
        }
        if (response.status.value == VoicemailContract.Status.DATA_CHANNEL_STATE_NO_CONNECTION) return -1
        return response.status.value
    }
}