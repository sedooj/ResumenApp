package com.sedooj.arch.viewmodel.auth.resume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
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

data class CreateResumeUiState(
    var title: String = "New resume",
    var vacancyInformation: CreateResumeUseCase.VacancyInformation? = null,
    var personalInformation: CreateResumeUseCase.PersonalInformation? = null,
    var workExperienceInformation: List<CreateResumeUseCase.WorkExperienceInformation>? = null,
    var skillsInformation: CreateResumeUseCase.SkillsInformation? = null,
    var resumeOptions: CreateResumeUseCase.ResumeOptionsComponent? = null,
)

data class TabsUiState(
    var selectedTab: TabsModel.Tabs = TabsModel.Tabs.PERSONAL_MAIN,
    var selectedTabId: Int = 0
)

@HiltViewModel
class CreateResumeViewModel @Inject constructor(
    private val resumeNetworkRepository: ResumeNetworkRepository,
) : ViewModel(), ResumeModel, TabsModel {
    private val _uiState = MutableStateFlow(CreateResumeUiState())
    val uiState: StateFlow<CreateResumeUiState> = _uiState.asStateFlow()

    private val _tabsState = MutableStateFlow(TabsUiState())
    val tabsState: StateFlow<TabsUiState> = _tabsState.asStateFlow()


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
                    socialMedia = input.socialMedia,
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
                    desiredSalary = input.desiredSalary,
                    busynessType = input.busynessType,
                    scheduleType = input.scheduleType,
                    readyForTravelling = input.readyForTravelling,
                    isChangesSaved = input.isChangesSaved
                )
            )
        }
    }

    override fun updateSkillsInformation(input: CreateResumeUseCase.SkillsInformation) {
        _uiState.update {
            it.copy(
                skillsInformation = CreateResumeUseCase.SkillsInformation(
                    softSkillsInformation = input.softSkillsInformation,
                    hardSkillsInformation = input.hardSkillsInformation,
                    workedFrameworksInformation = input.workedFrameworksInformation,
                    languagesSkillsInformation = input.languagesSkillsInformation,
                    workedProgrammingLanguageInformation = input.workedProgrammingLanguageInformation
                )
            )
        }
    }

    override fun updateWorkExperienceInformation(input: List<CreateResumeUseCase.WorkExperienceInformation>) {
        _uiState.update {
            it.copy(
                workExperienceInformation = input.map { work ->
                    CreateResumeUseCase.WorkExperienceInformation(
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
        viewModelScope.launch {
//            val convertedState = _uiState.value.convertStateToPush() ?: throw NullInputException()
//            resumeNetworkRepository.createResume(
//                input = convertedState
//            )
        }
    }



    override fun save() {
        TODO("Not yet implemented")
    }

    override fun setTab(tab: TabsModel.Tabs, id: Int) {
        _tabsState.update {
            it.copy(
                selectedTab = tab,
                selectedTabId = id
            )
        }
    }

}