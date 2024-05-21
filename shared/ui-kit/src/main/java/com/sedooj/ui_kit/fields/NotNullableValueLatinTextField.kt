package com.sedooj.ui_kit.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R
import java.nio.charset.Charset

@Composable
fun NotNullableValueLatinTextField(
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
        singleLine = true,
        maxLines = 1,
        supportingText = {
            if (value.isNullOrBlank())
                Text(
                    text = stringResource(id = R.string.field_must_not_be_empty),
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            if (!isValidCharacters(value))
                Text(
                    text = stringResource(id = R.string.field_must_contains_only_latin_chars),
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
        },
        shape = RoundedCornerShape(10.dp),
        isError = value.isNullOrBlank() || !isValidCharacters(value),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}

private fun isValidCharacters(s: String?): Boolean {
    return Charset.forName("US-ASCII").newEncoder().canEncode(s)
}