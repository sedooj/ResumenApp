package com.sedooj.localstorage.di

import com.sedooj.localstorage.dao.AuthUserDao
import com.sedooj.localstorage.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideAuthDao(appDatabase: AppDatabase): AuthUserDao {
        return appDatabase.authUserDao()
    }
}