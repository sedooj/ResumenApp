package com.sedooj.localstorage.repository

import android.content.Context
import com.sedooj.localstorage.db.AppDatabase

interface LocalStorage {
    suspend fun getDatabase(context: Context): AppDatabase
}