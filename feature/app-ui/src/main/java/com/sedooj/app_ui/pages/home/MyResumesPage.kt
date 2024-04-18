package com.sedooj.app_ui.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.sedooj.arch.viewmodel.auth.HomeViewModel
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.ResumeItemCard
import com.sedooj.ui_kit.ResumeItemState
import com.sedooj.ui_kit.Screen
import kotlinx.coroutines.launch


@Destination<RootGraph>(start = false, route = Routes.MY_RESUMES, style = ScreensTransitions::class)
@Composable
fun MyResumesScreen(
    destinationsNavigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = homeViewModel.uiState.collectAsState().value.resumeList
    LaunchedEffect(key1 = Unit) {
        homeViewModel.getResumeList()
    }
    val scope = rememberCoroutineScope()
    Screen(
        title = stringResource(id = string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        alignment = Alignment.Top
    ) {
        if (uiState.isNullOrEmpty()) {
            Text(text = stringResource(id = string.no_content_here_now))
        } else {
            uiState.forEach { resume ->
                ResumeItemCard(
                    modifier = Modifier.fillMaxWidth(),
                    resume = ResumeItemState(
                        resumeId = resume.resumeId, title = resume.title,
                    ),
                    onEditResume = {},
                    onDropResume = {
                        scope.launch {
                            homeViewModel.dropResume(resume.resumeId)
                        }
                    },
                    onDownloadResume = {}
                )
            }
        }
    }
}


