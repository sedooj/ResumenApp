package com.sedooj.app_ui.pages.resume.create

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.Routes
import com.sedooj.app_ui.pages.resume.create.components.CreateResumeComponentsPage
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME,
    style = ScreensTransitions::class
)
@Composable
fun CreateResumePage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel = hiltViewModel(),
) {
    val tabsUiState = createResumeViewModel.tabsState.collectAsState().value

    Screen(
        title = stringResource(id = string.create_resume),
        modifier = Modifier
            .fillMaxSize(),
        alignment = Alignment.Top,
        paddingValues = 0.dp
    ) {
        CreateResumeComponentsPage(
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}