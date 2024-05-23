package com.sedooj.app_ui.pages.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SignInDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.FadeScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.profile.UserViewModel
import com.sedooj.ui_kit.R.drawable
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.fields.FilledButton
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
        ProfileContent(
            modifier = Modifier.fillMaxWidth(),
            username = userViewModel.uiState.collectAsState().value.username ?: "User"
        )
    }
}


@Composable
private fun ProfileContent(modifier: Modifier = Modifier, username: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.Start
            )
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = drawable.profile),
                    contentDescription = stringResource(id = string.profile_icon),
                    modifier = Modifier
                        .size(120.dp)
                        .padding(30.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = username,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                    FilledButton(
                        label = stringResource(id = string.edit_profile),
                        onClick = {},
                        enabled = false
                    )
                }
            }

        }
    }
}