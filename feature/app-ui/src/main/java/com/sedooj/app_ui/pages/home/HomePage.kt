package com.sedooj.app_ui.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(start = false, route = Routes.HOME, style = ScreensTransitions::class)
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    Screen(
        title = stringResource(id = string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {        
        FilledButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = string.create_resume),
            onClick = {

            })
    }
}