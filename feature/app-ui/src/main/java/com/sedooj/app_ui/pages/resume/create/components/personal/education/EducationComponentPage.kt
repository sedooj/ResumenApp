package com.sedooj.app_ui.pages.resume.create.components.personal.education

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.dropUnlessResumed
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.EDUCATIONEDITORDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.personal.education.edit.EditorEducation
import com.sedooj.app_ui.pages.resume.create.components.personal.education.edit.EducationEditorPageContent
import com.sedooj.app_ui.pages.resume.create.components.personal.education.edit.createOrEdit
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(
    start = false,
    style = SlideScreenTransitions::class,
    route = Routes.EDUCATION
)
@Composable
fun EducationComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
    resultRecipient: ResultRecipient<EDUCATIONEDITORDestination, EditorEducation>,
) {
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
        floatingActionButton = {
            FloatingActionButton(onClick = dropUnlessResumed {
                createOrEdit(
                    navigator = navigator,
                    id = educationList?.lastIndex?.plus(1) ?: 0
                )
            }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "add edu")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        if (!educationList.isNullOrEmpty())
            EducationEditorPageContent(
                modifier = Modifier
                    .fillMaxSize(),
                educationList = educationList,
                onEdit = { i, edu ->
                    createOrEdit(
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
        else
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
                Text(
                    text = stringResource(id = R.string.put_information_about_education),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
            })
    }
}