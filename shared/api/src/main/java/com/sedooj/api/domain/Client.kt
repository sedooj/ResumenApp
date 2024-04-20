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

object NetworkConfig {
    private const val LOCAL_URL: String = "http://172.23.192.1:8080/"
    private const val REMOTE_URL: String = "http://217.71.129.139:4159/"
    private const val BASE_URL: String = REMOTE_URL
    private const val API_URL: String = "${BASE_URL}api/"
    const val AUTH_URL: String = "${BASE_URL}auth/"
    const val API_RESUME: String = "${API_URL}resume/"
}

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