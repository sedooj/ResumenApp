package com.sedooj.localstorage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sedooj.architecture.storage.entity.AuthUserEntity
import com.sedooj.localstorage.dao.AuthUserDao

@Database(entities = [AuthUserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authUserDao(): AuthUserDao

    suspend fun clearCredentials(authUserEntity: AuthUserEntity) {
        authUserDao().delete(authUserEntity)
    }
}