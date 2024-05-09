package com.sedooj.app_ui.pages.resume.create.components.vacancy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.vacancy.data.VacancyComponentData
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_VACANCY,
    style = SlideScreenTransitions::class
)
@Composable
fun VacancyComponentPage(
    modifier: Modifier = Modifier,
    createResumeViewModel: CreateResumeViewModel
) {
    val vacancyInformation = createResumeViewModel.uiState.collectAsState().value.vacancyInformation
    val vacancyComponentData = VacancyComponentData().rememberDataMap(initInfo = vacancyInformation)


}