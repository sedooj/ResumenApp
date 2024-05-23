package com.sedooj.api.domain

object NetworkConfig {
    private const val LOCAL_URL: String = "http://10.0.2.2:8080/"
    private const val REMOTE_URL: String = "http://217.71.129.139:4159/"
    private const val BASE_URL: String = REMOTE_URL
    private const val API_URL: String = "${BASE_URL}api/"
    const val AUTH_URL: String = "${BASE_URL}auth/"
    const val API_RESUME: String = "${API_URL}resume/"
}