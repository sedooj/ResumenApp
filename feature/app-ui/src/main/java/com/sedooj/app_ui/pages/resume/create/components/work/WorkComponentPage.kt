package com.sedooj.app_ui.pages.resume.create.components.work

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.dropUnlessResumed
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.WorkEditorDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.work.data.EditWork
import com.sedooj.app_ui.pages.resume.create.components.work.data.WorkListContent
import com.sedooj.app_ui.pages.resume.create.components.work.data.createOrEdit
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.FloatingAddButton
import com.sedooj.ui_kit.screens.Screen

@Destination<RootGraph>(
    start = false,
    style = SlideScreenTransitions::class,
    route = Routes.CREATE_RESUME_WORK
)
@Composable
fun WorkComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
    resultRecipient: ResultRecipient<WorkEditorDestination, EditWork>,
) {
    BackHandler {}
    val workExperienceList =
        createResumeViewModel.uiState.collectAsState().value.workExperienceInformation
    resultRecipient.onResult {
        createResumeViewModel.saveWork(
            it.id, CreateResumeUseCase.WorkExperienceInformation(
                companyName = it.company,
                kindOfActivity = it.kindOfActivity,
                gotJobDate = it.enterJobDate,
                quitJobDate = it.quitJobDate,
                isCurrentlyWorking = it.currentlyWorking
            )
        )
    }
    Screen(
        title = stringResource(id = R.string.work_experience),
        modifier = Modifier.fillMaxSize(),
        alignment = if (workExperienceList != null) Alignment.Top else Alignment.CenterVertically,
        hasBackButton = true,
        onBack = {
            navigator.navigateUp()
        },
        floatingActionButton = {
            FloatingAddButton(
                onClick = dropUnlessResumed {
                    createOrEdit(
                        navigator = navigator,
                        id = workExperienceList?.lastIndex?.plus(1) ?: 0
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        WorkListContent(modifier = Modifier
            .fillMaxSize(),
            workExperienceList = workExperienceList,
            onEdit = { i, work ->
                createOrEdit(
                    navigator = navigator,
                    id = i,
                    work = CreateResumeUseCase.WorkExperienceInformation(
                        companyName = work.companyName,
                        kindOfActivity = work.kindOfActivity,
                        gotJobDate = work.gotJobDate,
                        quitJobDate = work.quitJobDate,
                        isCurrentlyWorking = work.isCurrentlyWorking
                    )
                )
            }
        )
    }
}