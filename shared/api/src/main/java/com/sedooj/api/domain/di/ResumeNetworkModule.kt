package com.sedooj.api.domain.di

import com.sedooj.api.domain.api.ResumeNetworkRepositoryImpl
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResumeNetworkModule {
    @Binds
    abstract fun provide(
        resumeNetworkRepositoryImpl: ResumeNetworkRepositoryImpl
    ): ResumeNetworkRepository
}