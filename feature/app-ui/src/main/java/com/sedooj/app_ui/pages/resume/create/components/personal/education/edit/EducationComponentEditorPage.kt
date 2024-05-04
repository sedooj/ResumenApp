package com.sedooj.app_ui.pages.resume.create.components.personal.education.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(
    start = false,
    style = ScreensTransitions::class,
    route = Routes.EDUCATION_EDITOR
)
@Composable
fun EducationComponentEditorPage(
    navigator: DestinationsNavigator,
    education: CreateResumeUseCase.PersonalInformation.Education,
    createResumeViewModel: CreateResumeViewModel,
) {
    val title =
        education.title.ifBlank { stringResource(id = com.sedooj.ui_kit.R.string.education) }
    Screen(
        modifier = Modifier.fillMaxSize(),
        title = title,
        alignment = Alignment.Top,
        navigationButton = {

        }
    ) {
        Text("editor")
    }
}