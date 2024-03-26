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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
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
import com.sedooj.resumen.domain.repository.user.UsersNetworkRepository
import com.sedooj.resumen.domain.usecase.UsersNetworkRepositoryImpl
import com.sedooj.resumen.navigation.config.ScreensTransitions
import com.sedooj.resumen.navigation.pages.Routes
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation
import com.sedooj.resumen.viewmodel.AuthState
import com.sedooj.resumen.viewmodel.AuthorizationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
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
    val hasErrorState = rememberSaveable { mutableIntStateOf(0) }
    val client = remember { Client.create() }
    val usersNetworkRepository = remember { UsersNetworkRepositoryImpl(client = client) }
    val scope = rememberCoroutineScope()
    val authorizationViewModel = viewModel<AuthorizationViewModel>()
    val uiState = authorizationViewModel.uiState.collectAsState().value.state
    KitPageWithNavigation(
        title = uiState.name,
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
                TextComponents(hasError = hasErrorState.intValue, uiState)
                UsernameInputComponent(
                    value = usernameState.value,
                    hasError = hasErrorState.intValue
                ) {
                    if (hasErrorState.intValue != 0)
                        hasErrorState.intValue = 0
                    usernameState.value = it
                }
                PasswordInputComponent(
                    value = passwordState.value,
                    hasError = hasErrorState.intValue
                ) {
                    if (hasErrorState.intValue != 0)
                        hasErrorState.intValue = 0
                    passwordState.value = it
                }
                SignUpComponent(
                    enabled = uiState != AuthState.AUTHORIZATION && usernameState.value.isNotBlank() && passwordState.value.isNotBlank() && hasErrorState.intValue == 0,
                    toSignIn = {
                        destinationsNavigator.popBackStack()
                        destinationsNavigator.navigate(Routes.SIGN_IN)
                    }
                ) {
                    hasErrorState.intValue = authorizationViewModel.register(
                        input = CreateUserInput(
                            username = usernameState.value,
                            password = passwordState.value
                        ), usersNetworkRepository = usersNetworkRepository, scope = scope
                    )
                    if (
                        hasErrorState.intValue == 0
                    ) {
                        destinationsNavigator.popBackStack()
                        destinationsNavigator.navigate(Routes.HOME)
                    }
                }

            }
    }
}

@Composable
private fun TextComponents(
    hasError: Int,
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
    if (hasError != 0 && uiState != AuthState.AUTHORIZATION)
        Text(
            text = stringResource(id = hasError),
            fontWeight = FontWeight.Light,
            color = Color.Red,
            textAlign = TextAlign.Center
        )

}

@Composable
private fun UsernameInputComponent(
    value: String,
    hasError: Int,
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
        isError = hasError != 0,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}

@Composable
private fun PasswordInputComponent(
    value: String,
    hasError: Int,
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
        isError = hasError != 0
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

private fun register(
    username: String,
    password: String,
    usersNetworkRepository: UsersNetworkRepository,
    scope: CoroutineScope,
): Int {

    var response: Int = 0
    scope.launch {
        response = usersNetworkRepository.createUser(
            input = CreateUserInput(
                username = username, password = password
            )
        )
    }
    if (!scope.isActive)
        return when (response) {
            -1 -> R.string.no_connection
            200 -> 0
            400 -> R.string.uncorrect_input_data
            else -> R.string.unknown_error
        }
    return 1
}