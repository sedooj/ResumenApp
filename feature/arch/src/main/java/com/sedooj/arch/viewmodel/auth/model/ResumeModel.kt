package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.ResumeOptionsComponent
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.SkillsInformation
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.VacancyInformation
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.WorkExperienceInformation

interface ResumeModel {

    enum class CreationState {
        RESUME_OPTIONS,
        VACANCY,
        PERSONAL,
        WORK,
        SKILLS
    }

    fun updateResumeOptions(input: ResumeOptionsComponent)

    fun updateTitle(input: String)

    fun updatePersonalInformation(input: PersonalInformation)

    fun updateVacancyInformation(input: VacancyInformation)

    fun updateSkillsInformation(input: SkillsInformation)

    fun updateWorkExperienceInformation(input: List<WorkExperienceInformation>)

    fun dropUiState()

    suspend fun push()

    fun save()
}