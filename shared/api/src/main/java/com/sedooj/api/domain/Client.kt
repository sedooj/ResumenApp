package com.sedooj.api.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.encodeUtf8

interface Client {

    companion object {
        fun create(): HttpClient {
            return HttpClient(CIO) {
                install(Logging) {
                    class ClientLogger : Logger {
                        override fun log(message: String) {
                            println(message)
//                            Log.d("ClientLogger", message)
                        }
                    }

                    val clientLogger = ClientLogger()
                    logger = clientLogger
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        this.coerceInputValues = true
                        this.ignoreUnknownKeys = true
                    })
                }
                install(Auth) {
                    basic {
                        credentials {
                            BasicAuthCredentials(username = "jetbrains", password = "foobar")
                        }
                        realm = "Access to the '/' path"
                    }
                }
            }

        }
    }
}

fun getBasicHeader(
    auth: String
): String {
    val encoded = auth.encodeUtf8().base64()
    return "Basic $encoded"
}