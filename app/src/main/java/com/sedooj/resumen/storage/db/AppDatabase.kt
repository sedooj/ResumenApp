package com.sedooj.resumen.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sedooj.resumen.storage.dao.AuthUserDao
import com.sedooj.resumen.storage.entity.AuthUserEntity

@Database(entities = [AuthUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authUserDao(): AuthUserDao
}