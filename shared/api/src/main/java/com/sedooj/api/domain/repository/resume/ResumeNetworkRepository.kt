package com.sedooj.api.domain.repository.resume

import com.sedooj.api.domain.Client
import com.sedooj.api.domain.data.resume.entity.CreateResume
import com.sedooj.api.domain.data.resume.entity.Resume

interface ResumeNetworkRepository : Client {

    suspend fun createResume(input: CreateResume): String
    suspend fun updateResume(resumeId: Long, input: CreateResume): String
    suspend fun getResumeList(): List<Resume>
    suspend fun getResume(resumeId: Long): Resume
    suspend fun dropResume(resumeId: Long)
    suspend fun downloadResume(resumeId: Long): ByteArray
}