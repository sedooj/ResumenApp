package com.sedooj.arch.viewmodel.auth.resume

import androidx.lifecycle.ViewModel
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.ResumeOptionsComponent
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.SkillsInformation
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.VacancyInformation
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.WorkExperienceInformation
import com.sedooj.api.domain.repository.resume.ResumeNetworkRepository
import com.sedooj.arch.viewmodel.auth.exceptions.NullInputException
import com.sedooj.arch.viewmodel.auth.model.ResumeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CreateResumeUiState(
    var title: String? = null,
    var vacancyInformation: VacancyInformation? = null,
    var personalInformation: PersonalInformation? = null,
    var workExperienceInformation: List<WorkExperienceInformation>? = null,
    var skillsInformation: SkillsInformation? = null,
    var resumeOptions: ResumeOptionsComponent? = null,
)

@HiltViewModel
class CreateResumeViewModel @Inject constructor(
    private val resumeNetworkRepository: ResumeNetworkRepository,
) : ViewModel(), ResumeModel {
    private val _uiState = MutableStateFlow(CreateResumeUiState())
    val uiState: StateFlow<CreateResumeUiState> = _uiState.asStateFlow()

    override fun updateResumeOptions(input: ResumeOptionsComponent) {
        _uiState.update {
            it.copy(
                resumeOptions = ResumeOptionsComponent(
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

    override fun updatePersonalInformation(input: PersonalInformation) {
        _uiState.update {
            it.copy(
                personalInformation = PersonalInformation(
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
                    socialMedia = input.socialMedia,
                    aboutMe = input.aboutMe,
                    personalQualities = input.personalQualities
                )
            )
        }
    }

    override fun updateVacancyInformation(input: VacancyInformation) {
        _uiState.update {
            it.copy(
                vacancyInformation = VacancyInformation(
                    stackType = input.stackType,
                    platformType = input.platformType,
                    desiredRole = input.desiredRole,
                    desiredSalary = input.desiredSalary,
                    busynessType = input.busynessType,
                    scheduleType = input.scheduleType,
                    readyForTravelling = input.readyForTravelling
                )
            )
        }
    }

    override fun updateSkillsInformation(input: SkillsInformation) {
        _uiState.update {
            it.copy(
                skillsInformation = SkillsInformation(
                    softSkillsInformation = input.softSkillsInformation,
                    hardSkillsInformation = input.hardSkillsInformation,
                    workedFrameworksInformation = input.workedFrameworksInformation,
                    languagesSkillsInformation = input.languagesSkillsInformation,
                    workedProgrammingLanguageInformation = input.workedProgrammingLanguageInformation
                )
            )
        }
    }

    override fun updateWorkExperienceInformation(input: List<WorkExperienceInformation>) {
        _uiState.update {
            it.copy(
                workExperienceInformation = input.map { work ->
                    WorkExperienceInformation(
                        companyName = work.companyName,
                        kindOfActivity = work.kindOfActivity,
                        gotJobDate = work.gotJobDate,
                        quitJobDate = work.quitJobDate,
                        isCurrentlyWorking = work.isCurrentlyWorking
                    )
                }
            )
        }
    }

    override fun dropUiState() {
        _uiState.update {
            it.copy(
                title = null,
                vacancyInformation = null,
                personalInformation = null,
                workExperienceInformation = null,
                skillsInformation = null,
                resumeOptions = null,
            )
        }
    }

    override suspend fun push() {
        val convertedState = _uiState.value.convertStateToPush() ?: throw NullInputException()
        resumeNetworkRepository.createResume(
            input = convertedState
        )
    }

    private fun CreateResumeUiState.convertStateToPush(): CreateResumeUseCase? {
        val state = _uiState.value
        val title = state.title ?: return null
        val vacancyInformation = state.vacancyInformation ?: return null
        val personalInformation = state.personalInformation ?: return null
        val workExperienceInformation = state.workExperienceInformation ?: return null
        val skillsInformation = state.skillsInformation ?: return null
        val resumeOptions = state.resumeOptions ?: return null
        return CreateResumeUseCase(
            title = title,
            vacancyInformation = vacancyInformation,
            personalInformation = personalInformation,
            workExperienceInformation = workExperienceInformation,
            skillsInformation = skillsInformation,
            resumeOptions = resumeOptions

        )
    }

    override fun save() {
        TODO("Not yet implemented")
    }


}