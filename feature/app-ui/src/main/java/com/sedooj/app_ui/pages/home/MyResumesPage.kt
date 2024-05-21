package com.sedooj.app_ui.pages.home

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.FadeScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.downloader.ResumeDownloader
import com.sedooj.arch.viewmodel.auth.HomeViewModel
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.components.ResumeItemCard
import com.sedooj.ui_kit.components.ResumeItemState
import com.sedooj.ui_kit.screens.Screen
import kotlinx.coroutines.launch

@Destination<RootGraph>(
    start = false,
    route = Routes.MY_RESUMES,
    style = FadeScreensTransitions::class
)
@Composable
fun MyResumesScreen(
    destinationsNavigator: DestinationsNavigator,
    homeViewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val downloader = ResumeDownloader(context)
    val uiState = homeViewModel.uiState.collectAsState().value.resumeList
    LaunchedEffect(uiState.isNullOrEmpty()) {
        homeViewModel.getResumeList()
    }
    val scope = rememberCoroutineScope()
    Screen(
        title = stringResource(id = string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        alignment = Alignment.Top
    ) {
        if (uiState.isNullOrEmpty()) {
            Text(text = stringResource(id = string.no_content_here_now))
        } else {
            uiState.forEach { resume ->
                var isDownloading by remember { mutableStateOf(false) }
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
                    onDownloadResume = {
                        scope.launch {
                            val credentials = homeViewModel.getCredentials()
                            val url = homeViewModel.downloadResume(resume.resumeId)
                            if (url != null && credentials != null) {
                                isDownloading = true
                                downloader.downloadFile(url = url, auth = credentials, fileName = resume.title)
                                Toast.makeText(context, "Загрузка файла ${resume.title}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    isDownloading = isDownloading
                )
            }
        }
    }
}


