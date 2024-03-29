package com.sedooj.resumen.pages.auth

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
import androidx.compose.ui.platform.LocalContext
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
import com.sedooj.resumen.domain.usecase.UsersNetworkRepositoryImpl
import com.sedooj.resumen.navigation.config.ScreensTransitions
import com.sedooj.resumen.pages.Routes
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation
import com.sedooj.resumen.viewmodel.SignInViewModel
import com.sedooj.resumen.viewmodel.models.AuthenticationModel.AuthState
import com.sedooj.resumen.viewmodel.models.AuthorizationInput

@Destination<RootGraph>(
    start = true,
    route = Routes.SIGN_IN,
    style = ScreensTransitions::class
)
@Composable
fun SignInPage(
    destinationsNavigator: DestinationsNavigator,
) {
    val usernameState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val client = remember { Client.create() }
    val usersNetworkRepository = remember { UsersNetworkRepositoryImpl(client = client) }
    val scope = rememberCoroutineScope()
    val signUpViewModel = viewModel<SignInViewModel>()
    val uiState = signUpViewModel.uiState.collectAsState().value.state
    val errorState = signUpViewModel.uiState.collectAsState().value.error
    val context = LocalContext.current

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
                SignInComponent(
                    enabled = usernameState.value.isNotBlank() && passwordState.value.isNotBlank() && errorState == null,
                    toSignUp = {
                        destinationsNavigator.navigate(Routes.SIGN_UP)
                    },
                    register = {
                        signUpViewModel.auth(
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
        text = stringResource(id = R.string.sign_in_into_account),
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
private fun SignInComponent(
    enabled: Boolean,
    toSignUp: () -> Unit,
    register: () -> Unit,
) {
    KitFilledButton(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(id = R.string.sign_in),
        enabled = enabled,
        onClick = {
            register()
        })
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.no_account),
            fontWeight = FontWeight.Light
        )
        Text(
            text = stringResource(id = R.string.sign_up),
            modifier = Modifier.clickable {
                toSignUp()
            },
            fontWeight = FontWeight.Bold
        )
    }
}