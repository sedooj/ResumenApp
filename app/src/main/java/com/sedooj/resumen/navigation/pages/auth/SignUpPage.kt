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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SIGNINDestination
import com.ramcosta.composedestinations.generated.destinations.SIGNUPDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.resumen.R
import com.sedooj.resumen.navigation.config.ScreensTransitions
import com.sedooj.resumen.navigation.pages.Routes
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation

@Destination<RootGraph>(
    start = false,
    route = Routes.SIGN_UP,
    style = ScreensTransitions::class
)
@Composable
fun SignUpPage(
    destinationsNavigator: DestinationsNavigator
) {
    val usernameState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val hasErrorState = rememberSaveable { mutableIntStateOf(0) }

    KitPageWithNavigation(
        title = stringResource(id = R.string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponents(hasError = hasErrorState.intValue)
            UsernameInputComponent(
                value = usernameState.value,
                hasError = hasErrorState.intValue
            ) {
                if (hasErrorState.intValue != 0)
                    hasErrorState.intValue = 0
                usernameState.value = it
            }
            PasswordInputComponent(value = passwordState.value, hasError = hasErrorState.intValue) {
                if (hasErrorState.intValue != 0)
                    hasErrorState.intValue = 0
                passwordState.value = it
            }
            SignUpComponent(
                enabled = usernameState.value.isNotBlank() && passwordState.value.isNotBlank() && hasErrorState.intValue == 0,
                toSignIn = {
                    destinationsNavigator.popBackStack()
                    destinationsNavigator.navigate(Routes.SIGN_IN)
                }
            ) {
                hasErrorState.intValue =
                    register(username = usernameState.value, password = passwordState.value)
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
    hasError: Int
) {
    Text(
        text = stringResource(id = R.string.create_account),
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(id = R.string.type_username_and_password),
        fontWeight = FontWeight.Light
    )
    if (hasError != 0)
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
    onValueChange: (String) -> Unit
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
    onValueChange: (String) -> Unit
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
    register: () -> Unit
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
        textAlign = TextAlign.Center
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

private fun register(username: String, password: String): Int {
    if (username.isBlank()) return R.string.wrong_username_or_password
    if (password.isBlank()) return R.string.wrong_username_or_password
    return 0
}