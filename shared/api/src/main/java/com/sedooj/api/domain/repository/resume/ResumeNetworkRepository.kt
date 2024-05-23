package com.sedooj.api.domain.repository.resume

import com.sedooj.api.domain.Client
import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase

interface ResumeNetworkRepository : Client {

    suspend fun createResume(input: CreateResumeUseCase): Int?
    suspend fun updateResume(input: CreateResumeUseCase): Int?
    suspend fun getResumeList(): List<Resume>?
    suspend fun getResume(resumeId: Long): Resume?
    suspend fun dropResume(resumeId: Long)
    suspend fun downloadResume(resumeId: Long): String?
}