package com.sedooj.arch.viewmodel.auth.model

interface UserModel {

    suspend fun loadPage()
    suspend fun logout(): Boolean

    suspend fun getCredentials(): String?
}