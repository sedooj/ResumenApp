package com.sedooj.ui_kit.textField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R

@Composable
fun PasswordTextField(
    value: String,
    errorState: Int?,
    onValueChange: (String) -> Unit,
) {
    val canSeePasswordState = rememberSaveable {
        mutableStateOf(false)
    }
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
        visualTransformation = if (!canSeePasswordState.value) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(10.dp),
        isError = errorState != null,
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 10.dp),
                onClick = {
                    canSeePasswordState.value = !canSeePasswordState.value
                },
            ) {
                AnimatedVisibility(
                    visible = !canSeePasswordState.value,
                    enter = scaleIn(tween(200)),
                    exit = scaleOut(tween(200))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.can_see_eye),
                        contentDescription = stringResource(id = R.string.show_content),
                        modifier = Modifier.size(25.dp)
                    )
                }
                AnimatedVisibility(
                    visible = canSeePasswordState.value,
                    enter = scaleIn(tween(200)),
                    exit = scaleOut(tween(200))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cant_see_eye),
                        contentDescription = stringResource(id = R.string.hide_content),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    )
}