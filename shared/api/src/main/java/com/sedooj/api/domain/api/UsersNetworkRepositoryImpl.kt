package com.sedooj.api.domain.api

import com.sedooj.api.domain.NetworkConfig
import com.sedooj.api.domain.data.user.create.CreateUserInput
import com.sedooj.api.domain.data.user.create.auth.AuthUserInput
import com.sedooj.api.domain.repository.user.UsersNetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UsersNetworkRepositoryImpl(
    private val client: HttpClient,
) : UsersNetworkRepository {
    override suspend fun auth(input: AuthUserInput): Int {
        val response = client.get("${NetworkConfig.AUTH_URL}sign-in") {
            contentType(ContentType.Application.Json)
            setBody(
                AuthUserInput(
                    username = input.username,
                    password = input.password
                )
            )
        }
        return response.status.value
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
        // TODO: Move this line or handle no==connection error by another variant
        //if (response.status.value == VoicemailContract.Status.DATA_CHANNEL_STATE_NO_CONNECTION) return -1
        return response.status.value
    }
}