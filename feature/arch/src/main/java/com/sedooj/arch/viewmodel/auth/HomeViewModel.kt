package com.sedooj.arch.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import com.sedooj.arch.viewmodel.auth.model.HomeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resumeNetworkRepository: ResumeNetworkRepository,
) : ViewModel(), HomeModel {
    override suspend fun createResume(input: CreateResumeUseCase): Int? {
        return resumeNetworkRepository.createResume(input = input)
    }

    override suspend fun updateResume(resumeId: Long, input: CreateResumeUseCase): Int? {
        return resumeNetworkRepository.updateResume(resumeId = resumeId, input = input)
    }

    override suspend fun getResumeList(): List<Resume>? {
        return resumeNetworkRepository.getResumeList()
    }

    override suspend fun getResume(resumeId: Long): Resume? {
        return resumeNetworkRepository.getResume(resumeId = resumeId)
    }

    override suspend fun dropResume(resumeId: Long) {
        resumeNetworkRepository.dropResume(resumeId = resumeId)
    }

    override suspend fun downloadResume(resumeId: Long): ByteArray? {
        return resumeNetworkRepository.downloadResume(resumeId = resumeId)
    }
}