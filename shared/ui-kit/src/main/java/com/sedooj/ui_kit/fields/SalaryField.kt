package com.sedooj.ui_kit.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R

@Composable
fun SalaryField(
    modifier: Modifier = Modifier,
    onFromValueChange: (String) -> Unit,
    onToValueChange: (String) -> Unit,
    fromValue: String,
    toValue: String,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val colors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = if (fromValue.isNotBlank() && toValue.isNotBlank()) MaterialTheme.colorScheme.surfaceTint else MaterialTheme.colorScheme.surfaceContainerHigh
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        FromField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            onValueChange = onFromValueChange,
            keyboardActions = keyboardActions,
            colors = colors,
            fromValue = fromValue
        )
        ToField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            onValueChange = onToValueChange,
            keyboardActions = keyboardActions,
            colors = colors,
            toValue = toValue
        )
    }
}

private fun handleValueChange(value: String, previousValue: String): String {
    val newChar = value.removePrefix(previousValue)
    if (newChar == "\b") {
        return if (value != "") {
            value
        } else {
            "0"
        }
    }
    if (newChar != "-" && newChar != " " && newChar != ".") {
        if (newChar == ",") {
            if (previousValue.isBlank()) {
                return value.replaceBefore(value, "0")
            } else if ("," !in previousValue) {
                return value
            }
        } else if (previousValue.startsWith("0") && previousValue.length > 1) {
            return value
        } else if (newChar == "0" && previousValue.isBlank()) {
            return value
        } else return previousValue
    } else return value
    return previousValue
}

@Composable
private fun FromField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    fromValue: String,
    keyboardActions: KeyboardActions = KeyboardActions(),
    colors: TextFieldColors
) {
    OutlinedTextField(
        modifier = modifier,
        value = fromValue,
        colors = colors,
        onValueChange = {
            onValueChange(handleValueChange(value = it, previousValue = fromValue))
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ruble),
                contentDescription = stringResource(
                    id = R.string.ruble
                ),
                modifier = Modifier.size(25.dp)
            )
        },
        leadingIcon = {
            Text(text = stringResource(id = R.string.from))
        },
        supportingText = {
            if (fromValue.isBlank())
                Text(
                    text = stringResource(id = R.string.field_must_not_be_empty),
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        isError = (fromValue.isBlank()),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = keyboardActions
    )
}

@Composable
private fun ToField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    toValue: String,
    keyboardActions: KeyboardActions = KeyboardActions(),
    colors: TextFieldColors
) {
    OutlinedTextField(
        modifier = modifier,
        value = toValue,
        colors = colors,
        onValueChange = {
            val newChar = it.removePrefix(toValue)
            if (newChar == "\b") {
                if (it != "") {
                    onValueChange(it)
                } else {
                    onValueChange("0")
                }
            }
            if (newChar != "-" && newChar != " " && newChar != ".") {
                if (newChar == ",") {
                    if (toValue.isBlank()) {
                        onValueChange(it.replaceBefore(it, "0"))
                    } else if ("," !in toValue) {
                        onValueChange(it)
                    }
                } else {
                    if (toValue.startsWith("0") && toValue.length > 1) {
                        onValueChange(it)
                    } else if (newChar == "0" && toValue.isBlank()) {
                        onValueChange(it)
                    } else {
                        onValueChange(it.removePrefix("0"))
                    }
                }
            }
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ruble),
                contentDescription = stringResource(
                    id = R.string.ruble
                ),
                modifier = Modifier.size(25.dp)
            )
        },
        leadingIcon = {
            Text(text = stringResource(id = R.string.to))
        },
        supportingText = {
            if (toValue.isBlank())
                Text(
                    text = stringResource(id = R.string.field_must_not_be_empty),
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        isError = (toValue.isBlank()),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = keyboardActions
    )
}