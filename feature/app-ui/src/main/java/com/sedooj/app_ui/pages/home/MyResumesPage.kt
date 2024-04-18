package com.sedooj.app_ui.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.Routes
import com.sedooj.arch.viewmodel.auth.HomeViewModel
import com.sedooj.ui_kit.FilledIconButton
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.R.string
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
            .padding(20.dp)
    ) {
        if (uiState.isNullOrEmpty()) {
            Text(text = stringResource(id = string.no_content_here_now))
        } else {
            uiState.forEachIndexed { index, resume ->
                Box(modifier = Modifier.fillMaxWidth(), content = {
                    Column {
                        Text(text = resume.title)
                        Row {
                            FilledIconButton(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                icon = painterResource(id = R.drawable.edit),
                                onClick = {

                                }
                            )
                            FilledIconButton(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                onClick = {
                                    scope.launch {
                                        homeViewModel.dropResume(resumeId = resume.resumeId)
                                    }
                                },
                                icon = painterResource(id = R.drawable.trash)
                            )
                            FilledIconButton(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                onClick = {

                                },
                                icon = painterResource(id = R.drawable.download),
                                enabled = false
                            )
                        }
                    }
                })
            }
        }
    }
}


