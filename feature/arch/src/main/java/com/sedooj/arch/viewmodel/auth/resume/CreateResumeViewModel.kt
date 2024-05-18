package com.sedooj.arch.viewmodel.auth.resume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedooj.api.domain.data.resume.generator.Templates
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.LanguageKnowledgeLevelType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.ScheduleType
import com.sedooj.api.domain.data.types.StackType
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import com.sedooj.arch.viewmodel.auth.model.ResumeModel
import com.sedooj.arch.viewmodel.auth.model.TabsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//data class CreateResumeUiState(
//    var title: String = "New resume",
//    var vacancyInformation: CreateResumeUseCase.VacancyInformation? = null,
//    var personalInformation: CreateResumeUseCase.PersonalInformation? = null,
//    var workExperienceInformation: List<CreateResumeUseCase.WorkExperienceInformation>? = null,
//    var skillsInformation: CreateResumeUseCase.SkillsInformation? = null,
//    var resumeOptions: CreateResumeUseCase.ResumeOptionsComponent? = null,
//)
data class CreateResumeUiState(
    var title: String = "My test resume",
    var vacancyInformation: CreateResumeUseCase.VacancyInformation? = CreateResumeUseCase.VacancyInformation(
        stackType = StackType.FRONTEND,
        platformType = PlatformType.NOT_SELECTED,
        desiredRole = "consectetuer",
        desiredSalaryFrom = "decore",
        desiredSalaryTo = "has",
        busynessType = BusynessType.FULL,
        scheduleType = ScheduleType.SHIFT_METHOD,
        readyForTravelling = false

    ),
    var personalInformation: CreateResumeUseCase.PersonalInformation? = CreateResumeUseCase.PersonalInformation(
        firstName = "Krista Hinton",
        secondName = "Ollie Benjamin",
        thirdName = null,
        dateOfBirth = "id",
        city = "Heritage Park",
        residenceCountry = "Morocco",
        genderType = GenderType.NOT_SELECTED,
        maritalStatus = MaritalStatus.NOT_SELECTED,
        education = listOf(),
        hasChild = false,
        email = "alfonso.cline@example.com",
        aboutMe = null,
        personalQualities = "prompta"
    ),
    var workExperienceInformation: List<CreateResumeUseCase.WorkExperienceInformation>? = listOf(
        CreateResumeUseCase.WorkExperienceInformation(
            companyName = "Ty Nguyen",
            kindOfActivity = "cursus",
            gotJobDate = "adolescens",
            quitJobDate = "ex",
            isCurrentlyWorking = false
        )
    ),
    var skillsInformation: CreateResumeUseCase.SkillsInformation? = CreateResumeUseCase.SkillsInformation(
        languagesSkillsInformation = listOf(CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation(
            languageName = "Burton Meyers", knowledgeLevel = LanguageKnowledgeLevelType.A1

        )), programmingLanguagesSkillsInformation = listOf(CreateResumeUseCase.SkillsInformation.ProgrammingLanguageSkillsInformation(
            languageName = "Burt Dickson"
        ))

    ),
    var resumeOptions: CreateResumeUseCase.ResumeOptionsComponent? = null,
)

@HiltViewModel
class CreateResumeViewModel @Inject constructor(
    private val resumeNetworkRepository: ResumeNetworkRepository,
) : ViewModel(), ResumeModel {
    private val _uiState = MutableStateFlow(CreateResumeUiState())
    val uiState: StateFlow<CreateResumeUiState> = _uiState.asStateFlow()

    fun isFieldsFilled(): Boolean {
        val data = uiState.value
        if (data.title.isEmpty()) return false
        if (data.vacancyInformation == null) return false
        if (data.personalInformation == null) return false
        if (data.resumeOptions == null) return false
        if (data.skillsInformation == null) return false
        if (data.workExperienceInformation == null) return false
        return true
    }

    override fun updateResumeOptions(input: CreateResumeUseCase.ResumeOptionsComponent) {
        _uiState.update {
            it.copy(
                resumeOptions = CreateResumeUseCase.ResumeOptionsComponent(
                    generatePreview = input.generatePreview,
                    generateFinalResult = input.generateFinalResult,
                    generationTemplate = input.generationTemplate
                )
            )
        }
    }

    override fun updateTitle(input: String) {
        _uiState.update {
            it.copy(
                title = input
            )
        }
    }

    override fun updatePersonalInformation(input: CreateResumeUseCase.PersonalInformation) {
        _uiState.update {
            it.copy(
                personalInformation = CreateResumeUseCase.PersonalInformation(
                    firstName = input.firstName,
                    secondName = input.secondName,
                    thirdName = input.thirdName,
                    dateOfBirth = input.dateOfBirth,
                    city = input.city,
                    residenceCountry = input.residenceCountry,
                    genderType = input.genderType,
                    maritalStatus = input.maritalStatus,
                    education = input.education,
                    hasChild = input.hasChild,
                    email = input.email,
                    aboutMe = input.aboutMe,
                    personalQualities = input.personalQualities
                )
            )
        }
    }

    override fun updateVacancyInformation(input: CreateResumeUseCase.VacancyInformation) {
        _uiState.update {
            it.copy(
                vacancyInformation = CreateResumeUseCase.VacancyInformation(
                    stackType = input.stackType,
                    platformType = input.platformType,
                    desiredRole = input.desiredRole,
                    desiredSalaryFrom = input.desiredSalaryFrom,
                    desiredSalaryTo = input.desiredSalaryTo,
                    busynessType = input.busynessType,
                    scheduleType = input.scheduleType,
                    readyForTravelling = input.readyForTravelling
                )
            )
        }
    }

    override fun saveLanguageSkill(
        index: Int,
        input: CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation,
    ) {
        val newLanguageSkillsList =
            (_uiState.value.skillsInformation?.languagesSkillsInformation
                ?: emptyList()).toMutableList()
        if (newLanguageSkillsList.size <= index || newLanguageSkillsList.isEmpty())
            newLanguageSkillsList.add(input)
        else
            newLanguageSkillsList[index] = input
        _uiState.update {
            it.copy(
                skillsInformation = CreateResumeUseCase.SkillsInformation(
                    languagesSkillsInformation = newLanguageSkillsList.toList(),
                    programmingLanguagesSkillsInformation = _uiState.value.skillsInformation?.programmingLanguagesSkillsInformation
                )
            )
        }
    }

    override fun saveProgrammingLanguageSkill(
        index: Int,
        input: CreateResumeUseCase.SkillsInformation.ProgrammingLanguageSkillsInformation,
    ) {
        val newProgrammingLanguageSkillsList =
            (_uiState.value.skillsInformation?.programmingLanguagesSkillsInformation
                ?: emptyList()).toMutableList()
        if (newProgrammingLanguageSkillsList.size <= index || newProgrammingLanguageSkillsList.isEmpty())
            newProgrammingLanguageSkillsList.add(input)
        else
            newProgrammingLanguageSkillsList[index] = input
        _uiState.update {
            it.copy(
                skillsInformation = CreateResumeUseCase.SkillsInformation(
                    languagesSkillsInformation = _uiState.value.skillsInformation?.languagesSkillsInformation,
                    programmingLanguagesSkillsInformation = newProgrammingLanguageSkillsList.toList()
                )
            )
        }
    }


    override fun saveWork(index: Int, input: CreateResumeUseCase.WorkExperienceInformation) {
        val newWorkExperienceList =
            (_uiState.value.workExperienceInformation ?: emptyList()).toMutableList()
        if (newWorkExperienceList.size <= index || newWorkExperienceList.isEmpty())
            newWorkExperienceList.add(input)
        else
            newWorkExperienceList[index] = input
        _uiState.update {
            it.copy(
                workExperienceInformation = newWorkExperienceList.toList()
            )
        }
    }

    override fun saveEducation(
        index: Int,
        input: CreateResumeUseCase.PersonalInformation.Education,
    ) {
        val newEducationList =
            (_uiState.value.personalInformation?.education ?: emptyList()).toMutableList()
        if (newEducationList.size <= index || newEducationList.isEmpty())
            newEducationList.add(input)
        else
            newEducationList[index] = input
        _uiState.update {
            it.copy(
                personalInformation = CreateResumeUseCase.PersonalInformation(
                    firstName = _uiState.value.personalInformation?.firstName ?: "",
                    secondName = _uiState.value.personalInformation?.secondName ?: "",
                    thirdName = _uiState.value.personalInformation?.thirdName ?: "",
                    dateOfBirth = _uiState.value.personalInformation?.dateOfBirth ?: "",
                    city = _uiState.value.personalInformation?.city ?: "",
                    residenceCountry = _uiState.value.personalInformation?.residenceCountry ?: "",
                    genderType = _uiState.value.personalInformation?.genderType
                        ?: GenderType.NOT_SELECTED,
                    maritalStatus = _uiState.value.personalInformation?.maritalStatus
                        ?: MaritalStatus.NOT_SELECTED,
                    education = newEducationList.toList(),
                    hasChild = _uiState.value.personalInformation?.hasChild ?: false,
                    email = _uiState.value.personalInformation?.email ?: "",
                    aboutMe = _uiState.value.personalInformation?.aboutMe,
                    personalQualities = _uiState.value.personalInformation?.personalQualities ?: ""
                )
            )
        }
    }

    override fun dropUiState() {
        _uiState.update {
            it.copy(
                title = "New resume",
                vacancyInformation = null,
                personalInformation = null,
                workExperienceInformation = null,
                skillsInformation = null,
                resumeOptions = null,
            )
        }
    }

    override suspend fun push() {
        val value = uiState.value
        viewModelScope.launch {
            resumeNetworkRepository.createResume(
                input = CreateResumeUseCase(
                    title = value.title,
                    vacancyInformation = value.vacancyInformation!!,
                    personalInformation = value.personalInformation!!,
                    workExperienceInformation = value.workExperienceInformation!!,
                    skillsInformation = value.skillsInformation!!,
                    resumeOptions = CreateResumeUseCase.ResumeOptionsComponent(
                        generatePreview = true,
                        generateFinalResult = true,
                        generationTemplate = Templates.FREE

                    )
                )
            )
        }
    }


    override fun save() {
        TODO("Not yet implemented")
    }
}