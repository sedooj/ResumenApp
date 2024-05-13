package com.sedooj.app_ui.pages.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SIGNINCONFIRMATIONDestination
import com.ramcosta.composedestinations.generated.destinations.SIGNUPDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.Client
import com.sedooj.api.domain.api.UsersNetworkRepositoryImpl
import com.sedooj.app_ui.navigation.config.FadeScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.SignInViewModel
import com.sedooj.arch.viewmodel.auth.model.AuthenticationModel.AuthState
import com.sedooj.arch.viewmodel.auth.model.AuthorizationInput
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.screens.Screen
import com.sedooj.ui_kit.textField.PasswordTextField
import com.sedooj.ui_kit.textField.UsernameTextField

@Destination<RootGraph>(
    start = true,
    route = Routes.SIGN_IN,
    style = FadeScreensTransitions::class
)
@Composable
fun SignInPage(
    destinationsNavigator: DestinationsNavigator,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {
    val usernameState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val client = remember { Client.create() }
    val usersNetworkRepository = remember { UsersNetworkRepositoryImpl(client = client) }
    val scope = rememberCoroutineScope()
    val uiState = signInViewModel.uiState.collectAsState().value.state
    val errorState = signInViewModel.uiState.collectAsState().value.error
    val coldStartAttempted = signInViewModel.uiState.collectAsState().value.coldStartAttempted
    val context = LocalContext.current

    LaunchedEffect(key1 = coldStartAttempted) {
        if (!coldStartAttempted) {
            signInViewModel.coldAuth(usersNetworkRepository, scope)
        }
    }

    LaunchedEffect(key1 = uiState) {
        if (uiState == AuthState.AUTHORIZED) {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(SIGNINCONFIRMATIONDestination)
        }
    }

    Screen(
        title = stringResource(id = string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        if (uiState == AuthState.AUTHORIZATION || uiState == AuthState.AUTHORIZED) {
            CircularProgressIndicator(strokeCap = StrokeCap.Round)
            Text(
                text = stringResource(id = string.logging_in),
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Medium
            )
        } else {
            TextComponents(errorState = errorState, uiState)
            UsernameTextField(
                value = usernameState.value,
                hasError = errorState
            ) {
                signInViewModel.resetErrorState()
                usernameState.value = it
            }
            PasswordTextField(value = passwordState.value,
                errorState = errorState, onValueChange = {
                    signInViewModel.resetErrorState()
                    passwordState.value = it
                })
            SignInComponent(
                enabled = usernameState.value.isNotBlank() && passwordState.value.isNotBlank() && (errorState == null || errorState == string.no_connection),
                toSignUp = {
                    destinationsNavigator.navigate(SIGNUPDestination)
                },
                login = {
                    signInViewModel.auth(
                        input = AuthorizationInput(
                            username = usernameState.value,
                            password = passwordState.value
                        ),
                        scope = scope,
                        usersNetworkRepository = usersNetworkRepository,
                        context = context
                    )
                }
            )
        }
    }
}

@Composable
private fun TextComponents(
    errorState: Int?,
    uiState: AuthState,
) {
    Text(
        text = stringResource(id = string.sign_in_into_account),
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(id = string.type_username_and_password),
        fontWeight = FontWeight.Light
    )
    if (errorState != null && uiState != AuthState.AUTHORIZATION)
        Text(
            text = stringResource(id = errorState),
            fontWeight = FontWeight.Light,
            color = Color.Red,
            textAlign = TextAlign.Center
        )

}

@Composable
private fun SignInComponent(
    enabled: Boolean,
    toSignUp: () -> Unit,
    login: () -> Unit,
) {
    FilledButton(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(id = string.sign_in),
        enabled = enabled,
        onClick = {
            login()
        })
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(id = string.no_account),
            fontWeight = FontWeight.Light
        )
        Text(
            text = stringResource(id = string.sign_up),
            modifier = Modifier.clickable {
                toSignUp()
            },
            fontWeight = FontWeight.Bold
        )
    }
}