package com.sedooj.ui_kit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateButton(
    title: String,
    modifier: Modifier = Modifier,
    label: String,
    onEnterDate: (String?) -> Unit,
) {
    val datePicker = rememberDatePickerState()
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    datePicker.displayMode = DisplayMode.Input
    val defaultLocale = CalendarLocale.getDefault()
    val formatter = remember { DatePickerDefaults.dateFormatter() }

    val checkedColors = OutlinedTextFieldDefaults.colors(
        disabledBorderColor = MaterialTheme.colorScheme.surfaceTint,
        disabledLabelColor = MaterialTheme.colorScheme.surfaceTint,
        disabledTextColor = MaterialTheme.colorScheme.surfaceTint,
        disabledTrailingIconColor = MaterialTheme.colorScheme.surfaceTint
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
            readOnly = true,
            modifier = modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    isAlertDialogVisible = true
                },
            colors = checkedColors,
        )
        if (isAlertDialogVisible)
            AlertDialog(
                onDismissRequest = { isAlertDialogVisible = false },
                confirmButton = {
                    FilledButton(label = stringResource(id = R.string.save), onClick = {
                        isAlertDialogVisible = false
                        onEnterDate(
                            formatter.formatDate(
                                datePicker.selectedDateMillis,
                                defaultLocale
                            )
                        )
                    })
                },
                text = {
                    DatePicker(
                        state = datePicker,
                        title = {
                            Text(
                                text = stringResource(id = R.string.birth_date),
                                color = MaterialTheme.colorScheme.surfaceTint,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        })
                })
    }

}