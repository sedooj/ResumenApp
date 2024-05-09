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
import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEDestination
import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEPERSONALMAINDestination
import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEPERSONALSECONDARYDestination
import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEVACANCYDestination
import com.ramcosta.composedestinations.generated.destinations.EDUCATIONDestination
import com.ramcosta.composedestinations.generated.destinations.EDUCATIONEDITORDestination
import com.ramcosta.composedestinations.generated.destinations.MYRESUMESDestination
//import com.ramcosta.composedestinations.generated.destinations.CREATERESUMEVACANCYDestination
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
                destination(CREATERESUMEDestination) {
                    dependency(createResumeViewModel)
                }
                destination(CREATERESUMEPERSONALMAINDestination) {
                    dependency(createResumeViewModel)
                }
                destination(CREATERESUMEPERSONALSECONDARYDestination) {
                    dependency(createResumeViewModel)
                }
                destination(EDUCATIONDestination) {
                    dependency(createResumeViewModel)
                }
                destination(EDUCATIONEDITORDestination) {
                    dependency(createResumeViewModel)
                }
                destination(MYRESUMESDestination) {
                    dependency(myResumesViewModel)
                }
                destination(CREATERESUMEVACANCYDestination) {
                    dependency(createResumeViewModel)
                }
            }
        )
    }
}