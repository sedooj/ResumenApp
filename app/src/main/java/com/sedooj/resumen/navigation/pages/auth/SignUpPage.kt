package com.sedooj.resumen.navigation.pages.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.resumen.R
import com.sedooj.resumen.domain.Client
import com.sedooj.resumen.domain.data.user.create.CreateUserInput
import com.sedooj.resumen.domain.usecase.UsersNetworkRepositoryImpl
import com.sedooj.resumen.navigation.config.ScreensTransitions
import com.sedooj.resumen.navigation.pages.Routes
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation
import com.sedooj.resumen.viewmodel.SignUpViewModel
import com.sedooj.resumen.viewmodel.models.AuthenticationModel.*
import com.sedooj.resumen.viewmodel.models.AuthorizationInput

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

    LaunchedEffect(key1 = uiState) {
        if (uiState == AuthState.AUTHORIZED) {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(Routes.HOME)
        }
    }

    KitPageWithNavigation(
        title = stringResource(id = R.string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (uiState == AuthState.AUTHORIZATION)
            CircularProgressIndicator(strokeCap = StrokeCap.Round)
        else
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextComponents(errorState = errorState, uiState)
                UsernameInputComponent(
                    value = usernameState.value,
                    hasError = errorState
                ) {
                    signUpViewModel.resetErrorState()
                    usernameState.value = it
                }
                PasswordInputComponent(
                    value = passwordState.value,
                    errorState = errorState
                ) {
                    signUpViewModel.resetErrorState()
                    passwordState.value = it
                }
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
                            ), usersNetworkRepository = usersNetworkRepository, scope = scope
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
        text = stringResource(id = R.string.create_account),
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(id = R.string.type_username_and_password),
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
private fun UsernameInputComponent(
    value: String,
    hasError: Int?,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = stringResource(id = R.string.username))
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        isError = hasError != null,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}

@Composable
private fun PasswordInputComponent(
    value: String,
    errorState: Int?,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = stringResource(id = R.string.password))
        },
        singleLine = true,
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(10.dp),
        isError = errorState != null
    )
}

@Composable
private fun SignUpComponent(
    enabled: Boolean,
    toSignIn: () -> Unit,
    register: () -> Unit,
) {
    KitFilledButton(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(id = R.string.sign_up),
        enabled = enabled,
        onClick = {
            register()
        })

    Text(
        modifier = Modifier.clickable {

        },
        text = stringResource(id = R.string.by_clicking_register_agreement),
        fontWeight = FontWeight.Light,
        textAlign = TextAlign.Center,
        fontSize = MaterialTheme.typography.bodySmall.fontSize
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.has_account),
            fontWeight = FontWeight.Light
        )
        Text(
            text = stringResource(id = R.string.sign_in),
            modifier = Modifier.clickable {
                toSignIn()
            },
            fontWeight = FontWeight.Bold
        )
    }
}