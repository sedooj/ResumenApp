package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase

interface HomeModel {

    suspend fun createResume(input: CreateResumeUseCase): Int?
    suspend fun updateResume(resumeId: Long, input: CreateResumeUseCase): Int?
    suspend fun getResumeList(): List<Resume>?
    suspend fun getResume(resumeId: Long): Resume?
    suspend fun dropResume(resumeId: Long)
    suspend fun downloadResume(resumeId: Long): ByteArray?
}