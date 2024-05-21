package com.sedooj.app_ui.pages.resume.create.components.vacancy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.vacancy.data.VacancyPageContent
import com.sedooj.app_ui.pages.resume.create.components.vacancy.data.parseVacancyData
import com.sedooj.app_ui.pages.resume.create.components.vacancy.data.rememberVacancyDataMap
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.LostDataAlert
import com.sedooj.ui_kit.screens.Screen

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
    val vacancyInformation = createResumeViewModel.uiState.collectAsState().value.vacancyInformation
    val data = rememberVacancyDataMap(initInfo = vacancyInformation)
    var isDataEdited by remember { mutableStateOf(false) }
    var isLostDataAlertShow by remember { mutableStateOf(false) }
    var isDataSaved by remember { mutableStateOf(false) }
    Screen(
        title = stringResource(id = R.string.vacancy),
        alertDialog = {
            LostDataAlert(
                onDismiss = { isLostDataAlertShow = false },
                onConfirm = {
                    isLostDataAlertShow = false
                    navigator.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        showAlert = isLostDataAlertShow,
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Top,
        hasBackButton = true,
        onBack = {
            if (isDataEdited && !isDataSaved)
                isLostDataAlertShow = true
            else
                navigator.navigateUp()
        },
        floatingActionButton = {
            val parsedData = parseVacancyData(data = data, initInfo = vacancyInformation)
            AnimatedVisibility(
                visible = !isDataSaved && isDataEdited, enter = scaleIn(tween(200)), exit = scaleOut(
                    tween(200)
                )
            ) {
                FloatingActionButton(onClick = {
                    createResumeViewModel.updateVacancyInformation(
                        input = CreateResumeUseCase.VacancyInformation(
                            stackType = parsedData.stackType,
                            platformType = parsedData.platformType,
                            desiredRole = parsedData.desiredRole,
                            desiredSalaryFrom = parsedData.desiredSalaryFrom,
                            desiredSalaryTo = parsedData.desiredSalaryTo,
                            busynessType = parsedData.busynessType,
                            scheduleType = parsedData.scheduleType,
                            readyForTravelling = parsedData.readyForTravelling
                        )
                    )
                    isDataSaved = true
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = stringResource(id = R.string.done)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) {
        VacancyPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            data = data, onValueChange = { field, value ->
                data[field] = value
                isDataEdited = true
                isDataSaved = false
            })
    }
}