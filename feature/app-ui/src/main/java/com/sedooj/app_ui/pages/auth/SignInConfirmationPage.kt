package com.sedooj.app_ui.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HOMEDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.ConfirmationState
import com.sedooj.arch.viewmodel.auth.SignInConfirmationViewModel
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.R.drawable
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(
    start = false,
    route = Routes.SIGN_IN_CONFIRMATION,
    style = SlideScreenTransitions::class
)
@Composable
fun SignInConfirmationPage(
    destinationsNavigator: DestinationsNavigator,
    signInConfirmationViewModel: SignInConfirmationViewModel = hiltViewModel(),
) {
    val uiState = signInConfirmationViewModel.uiState.collectAsState().value
    val isContentLoaded = uiState.isContentLoaded
    LaunchedEffect(key1 = isContentLoaded) {
        if (!isContentLoaded)
            signInConfirmationViewModel.loadData()
    }
    val confirmationState = uiState.confirmationState
    val usernameState = uiState.username
    LaunchedEffect(key1 = confirmationState) {
        if (confirmationState == ConfirmationState.APPROVED) {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(HOMEDestination)
        }
    }

    Screen(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Image(
            modifier = Modifier.requiredSize(208.dp),
            painter = painterResource(id = drawable.splash_screen_hand),
            contentDescription = stringResource(
                id = string.app_name
            )
        )
        if (usernameState.isBlank()) {
            CircularProgressIndicator(strokeCap = StrokeCap.Round)
            Text(
                text = stringResource(id = string.loading_data),
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Medium
            )
        } else {
            Text(
                text = buildAnnotatedString {
                    append(text = stringResource(id = string.welcome))
                    append(text = ", ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    ) {
                        append(usernameState)
                    }
                    append(text = "!")
                },
                overflow = TextOverflow.Ellipsis,
            )
            FilledButton(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(id = string.sign_in),
                onClick = {
                    signInConfirmationViewModel.approve()
                },
                enabled = confirmationState == ConfirmationState.NOT_SELECTED
            )
        }


    }
}