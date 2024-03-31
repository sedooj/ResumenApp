package com.sedooj.localstorage.api

import android.content.Context
import androidx.room.Room
import com.sedooj.localstorage.db.AppDatabase
import com.sedooj.localstorage.repository.LocalStorage

class LocalStorageImpl : LocalStorage {
    override suspend fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java, "resumen-app-db"
        ).build()
    }

}