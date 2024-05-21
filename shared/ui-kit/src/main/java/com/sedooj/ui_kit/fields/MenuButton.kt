package com.sedooj.ui_kit.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R

@Composable
fun MenuButton(
    title: String,
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    isChecked: Boolean,
    isExpanded: Boolean,
    content: @Composable () -> Unit,
) {
    val notCheckedColors = OutlinedTextFieldDefaults.colors(
        disabledBorderColor = MaterialTheme.colorScheme.error,
        disabledLabelColor = MaterialTheme.colorScheme.error,
        disabledTextColor = MaterialTheme.colorScheme.surfaceTint,
        disabledTrailingIconColor = MaterialTheme.colorScheme.error
    )
    val checkedColors = OutlinedTextFieldDefaults.colors(
        disabledBorderColor = MaterialTheme.colorScheme.surfaceTint,
        disabledLabelColor = MaterialTheme.colorScheme.surfaceTint,
        disabledTextColor = MaterialTheme.colorScheme.surfaceTint,
        disabledTrailingIconColor = MaterialTheme.colorScheme.surfaceTint
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = {
                Text(
                    text = label,
                    maxLines = 1
                )
            },
            enabled = false,
            value = title,
            onValueChange = {},
            readOnly = false,
            supportingText = {
                if (!isChecked)
                    Text(
                        text = label,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.error,
                        overflow = TextOverflow.Ellipsis
                    )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = stringResource(
                        id = R.string.drop_down_item_icon
                    ),
                    modifier = Modifier.rotate(
                        if (isExpanded) 0f else 270f
                    )
                )
            },
            modifier = modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClick()
                },
            colors = if (!isChecked) notCheckedColors else checkedColors,
        )
        content()
    }

}