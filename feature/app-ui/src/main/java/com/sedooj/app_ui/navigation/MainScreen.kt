package com.sedooj.app_ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.CreateResumeDestination
import com.ramcosta.composedestinations.generated.destinations.EducationDestination
import com.ramcosta.composedestinations.generated.destinations.EducationEditorDestination
import com.ramcosta.composedestinations.generated.destinations.PersonalMainDestination
import com.ramcosta.composedestinations.generated.destinations.PersonalSecondaryDestination
import com.ramcosta.composedestinations.generated.destinations.ResumeListDestination
import com.ramcosta.composedestinations.generated.destinations.SkillsLanguagesDestination
import com.ramcosta.composedestinations.generated.destinations.SkillsProgrammingLanguagesDestination
import com.ramcosta.composedestinations.generated.destinations.VacancyDestination
import com.ramcosta.composedestinations.generated.destinations.WorkDestination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.destination
import com.sedooj.app_ui.pages.home.bottomBar.AnimatedBottomBar
import com.sedooj.arch.viewmodel.auth.HomeViewModel
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedBottomBar(navController = navController)
        }
    ) {
        val createResumeViewModel : CreateResumeViewModel = hiltViewModel()
        val myResumesViewModel : HomeViewModel = hiltViewModel()
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            modifier = Modifier.padding(it),
            dependenciesContainerBuilder = {
                destination(CreateResumeDestination) {
                    dependency(createResumeViewModel)
                }
                destination(PersonalMainDestination) {
                    dependency(createResumeViewModel)
                }
                destination(PersonalSecondaryDestination) {
                    dependency(createResumeViewModel)
                }
                destination(EducationDestination) {
                    dependency(createResumeViewModel)
                }
                destination(EducationEditorDestination) {
                    dependency(createResumeViewModel)
                }
                destination(ResumeListDestination) {
                    dependency(myResumesViewModel)
                }
                destination(VacancyDestination) {
                    dependency(createResumeViewModel)
                }
                destination(WorkDestination) {
                    dependency(createResumeViewModel)
                }
                destination(SkillsLanguagesDestination) {
                    dependency(createResumeViewModel)
                }
                destination(SkillsProgrammingLanguagesDestination) {
                    dependency(createResumeViewModel)
                }
            }
        )
    }
}