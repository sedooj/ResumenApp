package com.sedooj.app_ui.pages.resume.create.components.vacancy

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.ConfirmationScreenTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_VACANCY,
    style = ConfirmationScreenTransitions::class
)
@Composable
fun VacancyComponentPage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {

}