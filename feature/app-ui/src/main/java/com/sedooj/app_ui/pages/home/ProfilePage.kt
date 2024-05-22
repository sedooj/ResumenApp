package com.sedooj.app_ui.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SignInDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.FadeScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.profile.UserViewModel
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.screens.Screen
import kotlinx.coroutines.launch

@Destination<RootGraph>(
    start = false,
    route = Routes.PROFILE,
    style = FadeScreensTransitions::class
)
@Composable
fun ProfileScreen(
    destinationsNavigator: DestinationsNavigator,
    userViewModel: UserViewModel,
) {
    val scope = rememberCoroutineScope()
    val uiState = userViewModel.uiState.collectAsState().value
    LaunchedEffect(key1 = !uiState.isPageLoaded) {
        userViewModel.loadPage()
    }
    Screen(
        title = if (uiState.username.isNullOrBlank()) stringResource(id = string.profile) else uiState.username,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        alignment = Alignment.Top,
        actionButton = {
            IconButton(onClick = {
                scope.launch {
                    val logout = userViewModel.logout()
                    if (logout) {
                        destinationsNavigator.popBackStack()
                        destinationsNavigator.navigate(SignInDestination)
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.ExitToApp, contentDescription = stringResource(
                        id = string.logout
                    ),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    ) {

    }
}


