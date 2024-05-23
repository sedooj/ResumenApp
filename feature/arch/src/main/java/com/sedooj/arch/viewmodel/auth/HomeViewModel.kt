package com.sedooj.arch.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.sedooj.api.domain.data.resume.entity.Resume
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import com.sedooj.arch.viewmodel.auth.model.HomeModel
import com.sedooj.localstorage.dao.AuthUserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeUiState(
    var resumeList: List<Resume>? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resumeNetworkRepository: ResumeNetworkRepository,
    private val authUserDao: AuthUserDao
) : ViewModel(), HomeModel {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()
    override suspend fun createResume(input: CreateResumeUseCase): Int? {
        return resumeNetworkRepository.createResume(input = input)
    }

    override suspend fun updateResume(resumeId: Long, input: CreateResumeUseCase): Int? {
        return resumeNetworkRepository.updateResume(input = input)
    }

    override suspend fun getResumeList() {
        val list = resumeNetworkRepository.getResumeList()
        _uiState.update {
            it.copy(
                resumeList = list
            )
        }
    }

    override suspend fun getResume(resumeId: Long): Resume? {
        return resumeNetworkRepository.getResume(resumeId = resumeId)
    }

    override suspend fun dropResume(resumeId: Long) {
        resumeNetworkRepository.dropResume(resumeId = resumeId)
        getResumeList()
    }

    override suspend fun downloadResume(resumeId: Long): String? {
        return resumeNetworkRepository.downloadResume(resumeId = resumeId)
    }

    suspend fun getCredentials(): String? {
        val auth = authUserDao.getAuthorizationData()
        return auth?.let { "${auth.username}:${auth.password}" }
    }
}