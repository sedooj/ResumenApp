package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase

interface ResumeModel {

    enum class CreationState {
        RESUME_OPTIONS,
        VACANCY,
        PERSONAL,
        WORK,
        SKILLS
    }

    fun updateResumeOptions(input: CreateResumeUseCase.ResumeOptionsComponent)

    fun updateTitle(input: String)

    fun updatePersonalInformation(input: CreateResumeUseCase.PersonalInformation)

    fun updateVacancyInformation(input: CreateResumeUseCase.VacancyInformation)

    fun updateSkillsInformation(input: CreateResumeUseCase.SkillsInformation)

    fun updateWorkExperienceInformation(input: List<CreateResumeUseCase.WorkExperienceInformation>)

    fun saveWork(index: Int, input: CreateResumeUseCase.WorkExperienceInformation)

    fun saveEducation(index: Int, input: CreateResumeUseCase.PersonalInformation.Education)

    fun dropUiState()

    suspend fun push()

    fun save()
}