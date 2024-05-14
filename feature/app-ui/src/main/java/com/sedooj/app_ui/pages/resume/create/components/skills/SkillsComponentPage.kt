package com.sedooj.app_ui.pages.resume.create.components.skills

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_SKILLS,
    style = SlideScreenTransitions::class
)
@Composable
fun SkillsComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {

}