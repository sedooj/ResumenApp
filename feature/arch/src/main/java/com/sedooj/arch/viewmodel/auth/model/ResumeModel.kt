package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository

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

    fun saveLanguageSkill(index: Int, input: CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation)
    fun saveProgrammingLanguageSkill(index: Int, input: CreateResumeUseCase.SkillsInformation.ProgrammingLanguageSkillsInformation)

    fun saveWork(index: Int, input: CreateResumeUseCase.WorkExperienceInformation)

    fun saveEducation(index: Int, input: CreateResumeUseCase.PersonalInformation.Education)

    fun dropUiState()

    suspend fun push()

    fun save()

    suspend fun parseData(input: CreateResumeUseCase)
}