package com.sedooj.localstorage.repository

import com.sedooj.architecture.storage.entity.AuthUserEntity

interface LocalStorage {

    suspend fun getAuthorizationData(): AuthUserEntity?

    suspend fun insert(auth: AuthUserEntity)

    suspend fun update(auth: AuthUserEntity)
}