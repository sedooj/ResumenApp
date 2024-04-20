package com.sedooj.ui_kit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SalaryTextField(
    label: Int,
    onValueChange: (String) -> Unit,
    value: String?,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value ?: "",
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = stringResource(id = label))
        },
        trailingIcon = {

        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        isError = false,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
    )
}