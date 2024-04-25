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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.Client
import com.sedooj.api.domain.api.UsersNetworkRepositoryImpl
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.SignUpViewModel
import com.sedooj.arch.viewmodel.auth.model.AuthenticationModel.AuthState
import com.sedooj.arch.viewmodel.auth.model.AuthorizationInput
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.Screen
import com.sedooj.ui_kit.textField.PasswordTextField
import com.sedooj.ui_kit.textField.UsernameTextField
import kotlinx.coroutines.launch

@Destination<RootGraph>(
    start = false,
    route = Routes.SIGN_UP,
    style = ScreensTransitions::class
)
@Composable
fun SignUpPage(
    destinationsNavigator: DestinationsNavigator,
) {
    val usernameState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val client = remember { Client.create() }
    val usersNetworkRepository = remember { UsersNetworkRepositoryImpl(client = client) }
    val scope = rememberCoroutineScope()
    val signUpViewModel = viewModel<SignUpViewModel>()
    val uiState = signUpViewModel.uiState.collectAsState().value.state
    val errorState = signUpViewModel.uiState.collectAsState().value.error
    val context = LocalContext.current

    Screen(
        title = stringResource(id = string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (uiState == AuthState.AUTHORIZED) {
            Text(
                text = stringResource(id = string.signed_up_successfully),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            FilledButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = string.sign_in),
                onClick = {
                    scope.launch {
                        destinationsNavigator.navigateUp()
                    }
                })
        } else {
            if (uiState == AuthState.AUTHORIZATION) {
                CircularProgressIndicator(strokeCap = StrokeCap.Round)
                Text(
                    text = stringResource(id = string.signing_up),
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
            } else {
                TextComponents(errorState = errorState, uiState)
                UsernameTextField(
                    value = usernameState.value,
                    hasError = errorState
                ) {
                    signUpViewModel.resetErrorState()
                    usernameState.value = it
                }
                PasswordTextField(value = passwordState.value,
                    errorState = errorState, onValueChange = {
                        signUpViewModel.resetErrorState()
                        passwordState.value = it
                    })
                SignUpComponent(
                    enabled = usernameState.value.isNotBlank() && passwordState.value.isNotBlank() && errorState == null,
                    toSignIn = {
                        destinationsNavigator.popBackStack()
                        destinationsNavigator.navigate(Routes.SIGN_IN)
                    },
                    register = {
                        signUpViewModel.auth(
                            input = AuthorizationInput(
                                username = usernameState.value,
                                password = passwordState.value
                            ),
                            usersNetworkRepository = usersNetworkRepository,
                            scope = scope,
                            context = context
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TextComponents(
    errorState: Int?,
    uiState: AuthState,
) {
    Text(
        text = stringResource(id = string.create_account),
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
private fun SignUpComponent(
    enabled: Boolean,
    toSignIn: () -> Unit,
    register: () -> Unit,
) {
    FilledButton(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(id = string.sign_up),
        enabled = enabled,
        onClick = {
            register()
        })

    Text(
        modifier = Modifier.clickable {

        },
        text = stringResource(id = string.by_clicking_register_agreement),
        fontWeight = FontWeight.Light,
        textAlign = TextAlign.Center,
        fontSize = MaterialTheme.typography.bodySmall.fontSize
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(id = string.has_account),
            fontWeight = FontWeight.Light
        )
        Text(
            text = stringResource(id = string.sign_in),
            modifier = Modifier.clickable {
                toSignIn()
            },
            fontWeight = FontWeight.Bold
        )
    }
}