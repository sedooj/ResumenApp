package com.sedooj.app_ui.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.R
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.Routes
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(start = false, route = Routes.PROFILE, style = ScreensTransitions::class)
@Composable
fun ProfileScreen(
    destinationsNavigator: DestinationsNavigator,
) {
    Screen(
        title = stringResource(id = string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = stringResource(id = string.no_content_here_now))
    }
}


