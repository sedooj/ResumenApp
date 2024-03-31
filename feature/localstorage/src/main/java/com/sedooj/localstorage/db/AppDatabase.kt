package com.sedooj.localstorage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sedooj.localstorage.dao.AuthUserDao
import com.sedooj.architecture.storage.entity.AuthUserEntity

@Database(entities = [AuthUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authUserDao(): AuthUserDao
}