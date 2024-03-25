package com.sedooj.resumen.domain

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkConfig {

    const val BASE_URL: String = "http://217.71.129.139:4159/"
    const val API_URL: String = "${BASE_URL}api/"
    const val AUTH_URL: String = "${BASE_URL}auth/"
    const val API_RESUME: String = "${API_URL}resume/"

}

interface Client {

    companion object {
        fun create(): HttpClient {
            return HttpClient(Android) {
                install(Logging) {
                    class ClientLogger : Logger {
                        override fun log(message: String) {
                            Log.d("ClientLogger", message)
                        }
                    }
                    val clientLogger = ClientLogger()
                    logger = clientLogger
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        this.coerceInputValues = true
                    })
                }
            }

        }
    }

}