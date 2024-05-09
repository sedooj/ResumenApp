package com.sedooj.app_ui.pages.resume.create.components.vacancy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.vacancy.data.VacancyComponent
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.ScreenWithAlert

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_VACANCY,
    style = SlideScreenTransitions::class
)
@Composable
fun VacancyComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    val vacancyComponent = VacancyComponent()
    val vacancyInformation = createResumeViewModel.uiState.collectAsState().value.vacancyInformation
    val data = vacancyComponent.dataMap(initInfo = vacancyInformation)
    var isDataEdited by remember { mutableStateOf(false) }
    var isLostDataAlertShow by remember { mutableStateOf(false) }
    ScreenWithAlert(
        title = stringResource(id = R.string.vacancy),
        alertDialog = { vacancyComponent.lostDataAlert() },
        showAlert = isLostDataAlertShow,
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Top
    ) {
        vacancyComponent.content(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            data = data, onValueChange = { field, value ->
                data[field] = value
                isDataEdited = true
            })
    }
}