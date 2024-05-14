package com.sedooj.app_ui.pages.resume.create.components.education

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.EducationEditorDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.education.data.EducationListComponent
import com.sedooj.app_ui.pages.resume.create.components.education.edit.data.EducationComponentData
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.screens.Screen

@Destination<RootGraph>(
    start = false,
    style = SlideScreenTransitions::class,
    route = Routes.CREATE_RESUME_EDUCATION
)
@Composable
fun EducationComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
    resultRecipient: ResultRecipient<EducationEditorDestination, EducationComponentData.EditorEducation>,
) {
    BackHandler {}
    val educationList =
        createResumeViewModel.uiState.collectAsState().value.personalInformation?.education
    resultRecipient.onResult {
        createResumeViewModel.saveEducation(
            it.id, Education(
                educationStage = it.educationStage,
                title = it.title,
                locationCity = it.locationCity,
                enterDate = it.enterDate,
                graduatedDate = it.graduatedDate,
                faculty = it.faculty,
                speciality = it.speciality
            )
        )
    }
    Screen(
        title = stringResource(id = R.string.education),
        modifier = Modifier.fillMaxSize(),
        alignment = if (educationList != null) Alignment.Top else Alignment.CenterVertically,
        hasBackButton = true,
        onBack = {
            navigator.navigateUp()
        },
        floatingActionButton = {
            EducationListComponent().FloatingActionButton(
                navigator = navigator,
                educationList = educationList
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        EducationListComponent().Content(modifier = Modifier
            .fillMaxSize(),
            educationList = educationList,
            onEdit = { i, edu ->
                EducationListComponent().createOrEdit(
                    navigator = navigator,
                    id = i,
                    education = Education(
                        educationStage = edu.educationStage,
                        title = edu.title,
                        locationCity = edu.locationCity,
                        enterDate = edu.enterDate,
                        graduatedDate = edu.graduatedDate,
                        faculty = edu.faculty,
                        speciality = edu.speciality
                    )
                )
            })
    }
}