package com.sedooj.localstorage.api

import com.sedooj.architecture.storage.entity.AuthUserEntity
import com.sedooj.localstorage.dao.AuthUserDao
import com.sedooj.localstorage.repository.LocalStorage
import javax.inject.Inject

class LocalStorageImpl @Inject constructor(
    private val authDao: AuthUserDao,
) : LocalStorage {

    override suspend fun getAuthorizationData(): AuthUserEntity? {
        return authDao.getAuthorizationData()
    }

    override suspend fun insert(auth: AuthUserEntity) {
        authDao.insert(auth = auth)
    }

    override suspend fun update(auth: AuthUserEntity) {
        authDao.update(auth = auth)
    }

}