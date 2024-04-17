package com.sedooj.api.domain.repository.resume

import com.sedooj.api.domain.Client
import com.sedooj.api.domain.data.resume.entity.CreateResume

interface ResumeNetworkRepository : Client {

    suspend fun createResume(input: CreateResume): String
}