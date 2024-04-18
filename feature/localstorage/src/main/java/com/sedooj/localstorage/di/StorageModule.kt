package com.sedooj.localstorage.di

import android.app.Application
import androidx.room.Room
import com.sedooj.localstorage.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    fun provide(app: Application): AppDatabase {
        return Room.databaseBuilder(
            context = app,
            AppDatabase::class.java, "resumen-app-db"
        ).build()
    }
}