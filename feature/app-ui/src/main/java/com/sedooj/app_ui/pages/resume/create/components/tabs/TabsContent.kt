package com.sedooj.app_ui.pages.resume.create.components.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
            }
        )

        TabsModel.Tabs.PERSONAL -> {


        }

        TabsModel.Tabs.WORK -> {


        }

        TabsModel.Tabs.SKILLS -> {


        }
    }
}