package com.sedooj.app_ui.pages.resume.create.components.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.sedooj.app_ui.pages.resume.create.components.PersonalComponent
import com.sedooj.app_ui.pages.resume.create.components.ResumeOptionsComponent
import com.sedooj.app_ui.pages.resume.create.components.VacancyComponent
import com.sedooj.arch.viewmodel.auth.model.TabsModel
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel

@Composable
fun TabContent(
    selectedTab: TabsModel.Tabs,
    createResumeViewModel: CreateResumeViewModel,
) {
    // Resume Options
    val title = rememberSaveable { mutableStateOf("") }
    // Vacancy information
    val vacancy = createResumeViewModel.uiState.collectAsState().value.vacancyInformation
    val stackType = rememberSaveable { mutableStateOf(vacancy?.stackType) }
    val platformType = rememberSaveable { mutableStateOf(vacancy?.platformType) }
    val desiredRole = rememberSaveable { mutableStateOf("") }
    val desiredSalary = rememberSaveable { mutableStateOf("0") }
    val busynessType = rememberSaveable { mutableStateOf(vacancy?.busynessType) }
    val scheduleType = rememberSaveable { mutableStateOf(vacancy?.scheduleType) }
    val readyForTravelling = rememberSaveable { mutableStateOf(false) }
    // Personal information
    val personal = createResumeViewModel.uiState.collectAsState().value.personalInformation
    val firstName = rememberSaveable { mutableStateOf(personal?.firstName) }
    val secondName = rememberSaveable { mutableStateOf(personal?.secondName) }
    val thirdName = rememberSaveable { mutableStateOf(personal?.thirdName) }
    val dateOfBirth = rememberSaveable { mutableStateOf(personal?.dateOfBirth) }
    val city = rememberSaveable { mutableStateOf(personal?.city) }
    val residenceCountry = rememberSaveable { mutableStateOf(personal?.residenceCountry) }
    val genderType = rememberSaveable { mutableStateOf(personal?.genderType) }
    val maritalStatus = rememberSaveable { mutableStateOf(personal?.maritalStatus) }
    val education = rememberSaveable { mutableStateOf(personal?.education) }
    val hasChild = rememberSaveable { mutableStateOf(personal?.hasChild) }
    val socialMedia = rememberSaveable { mutableStateOf(personal?.socialMedia) }
    val aboutMe = rememberSaveable { mutableStateOf(personal?.aboutMe) }
    val personalQualities = rememberSaveable { mutableStateOf(personal?.personalQualities) }
    val selectedPersonalTab = rememberSaveable { mutableStateOf(TabsModel.PersonalTabs.MAIN) }
    when (selectedTab) {
        TabsModel.Tabs.RESUME -> ResumeOptionsComponent(
            onValueChange = {
                title.value = it
            }, titleValue = title.value
        )

        TabsModel.Tabs.VACANCY -> VacancyComponent(
            stackType = stackType.value,
            platformType = platformType.value,
            onStackSelect = {
                stackType.value = it
            },
            onPlatformSelect = {
                platformType.value = it
            },
            desiredRole = desiredRole.value,
            onRoleValueChange = {
                desiredRole.value = it
            },
            desiredSalary = desiredSalary.value,
            onSalaryValueChange = {
                desiredSalary.value = it
            },
            busynessType = busynessType.value,
            onBusynessSelect = {
                busynessType.value = it
            },
            scheduleType = scheduleType.value,
            onScheduleSelect = {
                scheduleType.value = it
            },
            readyForTravelling = readyForTravelling.value,
            onReadyTravelValueChange = {
                readyForTravelling.value = !readyForTravelling.value
            }
        )

        TabsModel.Tabs.PERSONAL -> PersonalComponent(
            firstName = firstName.value,
            secondName = secondName.value,
            thirdName = thirdName.value,
            dateOfBirth = dateOfBirth.value,
            city = city.value,
            residenceCountry = residenceCountry.value,
            genderType = genderType.value,
            maritalStatus = maritalStatus.value,
            education = education.value,
            hasChild = hasChild.value,
            socialMedia = socialMedia.value,
            aboutMe = aboutMe.value,
            personalQualities = personalQualities.value,
            onDate = {
                dateOfBirth.value = it
            },
            onGenderType = {
                genderType.value = it
                if (maritalStatus.value != null) {
                    if (maritalStatus.value?.genderType != genderType.value) {
                        maritalStatus.value = null
                    }
                }
            },
            onMaritalType = {
                maritalStatus.value = it
            },
            selectedTab = selectedPersonalTab.value,
            createResumeViewModel = createResumeViewModel
        )

        TabsModel.Tabs.WORK -> {


        }

        TabsModel.Tabs.SKILLS -> {


        }
    }
}