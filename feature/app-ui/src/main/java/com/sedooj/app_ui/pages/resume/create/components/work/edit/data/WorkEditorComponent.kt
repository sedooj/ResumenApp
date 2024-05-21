package com.sedooj.app_ui.pages.resume.create.components.work.edit.data

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sedooj.app_ui.pages.resume.create.components.generic.BooleanConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.DateValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.app_ui.pages.resume.create.components.work.data.EditWork
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import java.util.Locale


enum class WorkEditorComponentPageFields(
    @StringRes
    val fieldName: Int,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    COMPANY(
        fieldName = R.string.company_name,
        readOnly = false
    ),
    ACTIVITY(
        fieldName = R.string.kind_of_activity,
        readOnly = false
    ),
    ENTER_JOB(
        fieldName = R.string.enter_job,
        readOnly = true
    ),
    QUIT_JOB(
        fieldName = R.string.quit_job,
        readOnly = true
    ),
    CURRENTLY_WORKING(
        fieldName = R.string.currently_working,
        readOnly = true,
        suggestions = listOf(
            CustomValue(
                BooleanConvertibleContainer(false)
            ),
            CustomValue(
                BooleanConvertibleContainer(true)
            ),
        )
    )
}

@Composable
fun rememberWorkEditorDataMap(initInfo: EditWork): SnapshotStateMap<WorkEditorComponentPageFields, FieldValue> {
    return remember {
        mutableStateMapOf(
            WorkEditorComponentPageFields.COMPANY to if (initInfo.company.isBlank()) TextValue(
                ""
            ) else TextValue(
                initInfo.company
            ),

            WorkEditorComponentPageFields.ACTIVITY to if (initInfo.kindOfActivity.isBlank()) DateValue(
                ""
            ) else DateValue(
                initInfo.kindOfActivity
            ),

            WorkEditorComponentPageFields.ENTER_JOB to if (initInfo.enterJobDate.isBlank()) DateValue(
                ""
            )
            else DateValue(initInfo.enterJobDate),
            WorkEditorComponentPageFields.QUIT_JOB to if (initInfo.quitJobDate.isBlank()) DateValue(
                ""
            )
            else DateValue(initInfo.quitJobDate),
            WorkEditorComponentPageFields.CURRENTLY_WORKING to CustomValue(
                BooleanConvertibleContainer(initInfo.currentlyWorking)
            )
        )
    }
}

@Composable
fun parseWorkEditorData(
    data: Map<WorkEditorComponentPageFields, FieldValue>,
    initInfo: EditWork,
): EditWork {
    val company =
        data[WorkEditorComponentPageFields.COMPANY]?.asStringValue() ?: initInfo.company
    val kindOfActivity =
        data[WorkEditorComponentPageFields.ACTIVITY]?.asStringValue() ?: initInfo.kindOfActivity
    val enterJob =
        data[WorkEditorComponentPageFields.ENTER_JOB]?.asStringValue() ?: initInfo.enterJobDate
    val quitJob =
        data[WorkEditorComponentPageFields.QUIT_JOB]?.asStringValue() ?: initInfo.quitJobDate
    val currentlyWorking =
        (data[WorkEditorComponentPageFields.CURRENTLY_WORKING]?.asInitialValue() as BooleanConvertibleContainer?)?.value
            ?: initInfo.currentlyWorking

    return EditWork(
        id = initInfo.id,
        company = company,
        kindOfActivity = kindOfActivity,
        enterJobDate = enterJob,
        quitJobDate = quitJob,
        currentlyWorking = currentlyWorking
    )
}

@Composable
private fun InputTextField(
    field: WorkEditorComponentPageFields,
    value: String,
    onValueChange: (FieldValue) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
) {
    NotNullableValueTextField(label = field.fieldName, onValueChange = {
        onValueChange(TextValue(it))
    }, value = value, modifier = modifier, readOnly = readOnly)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Field(
    field: WorkEditorComponentPageFields,
    value: FieldValue,
    onValueChange: (FieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    Column {
        InputTextField(
            field = field,
            value = value.asStringValue(),
            onValueChange = onValueChange,
            modifier = modifier.onFocusChanged {
                isFocused = it.isFocused && !showDateDialog
                if (value is DateValue && it.isFocused) {
                    showDateDialog = true
                }
            },
            readOnly = field.readOnly
        )
        if (field.readOnly && value is DateValue) {
            DatePicker(
                onDismiss = {
                    showDateDialog = false
                    isFocused = false
                },
                showDialog = showDateDialog,
                content = {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DatePickerComponent(
                            modifier = Modifier.fillMaxWidth(),
                            onDate = {
                                onValueChange(DateValue(it ?: ""))
                                isFocused = false
                                showDateDialog = false
                            },
                            title = field.fieldName,
                        )
                    }
                }
            )
        }

        AnimatedVisibility(visible = (isFocused && field.suggestions.isNotEmpty())) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                field.suggestions.forEach {
                    SuggestionChip(
                        onClick = {
                            onValueChange(it)
                        },
                        label = {
                            Text(
                                text = it.value.asStringValue(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        },
                        enabled = value.asStringValue() != it.value.asStringValue()
                    )
                }
            }
        }
    }
}

@Composable
private fun DatePicker(
    onDismiss: () -> Unit,
    showDialog: Boolean,
    content: @Composable () -> Unit,
) {
    if (showDialog)
        Dialog(onDismissRequest = onDismiss) {
            content()
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerComponent(
    modifier: Modifier = Modifier,
    onDate: (String?) -> Unit,
    @StringRes
    title: Int,
) {
    val datePicker = rememberDatePickerState()
    datePicker.displayMode = DisplayMode.Input
    val defaultLocale = CalendarLocale.getDefault(Locale.Category.FORMAT)
    val formatter = remember { DatePickerDefaults.dateFormatter() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.DatePicker(
            state = datePicker,
            title = {
                Text(
                    text = stringResource(id = title),
                    color = MaterialTheme.colorScheme.surfaceTint,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            dateFormatter = formatter,
            showModeToggle = true
        )
        FilledButton(label = stringResource(id = R.string.save), onClick = {
            onDate(formatter.formatDate(datePicker.selectedDateMillis, defaultLocale))
        })
    }
}

@Composable
fun WorkEditorPageContent(
    modifier: Modifier = Modifier,
    onEdit: (WorkEditorComponentPageFields, FieldValue) -> Unit,
    data: Map<WorkEditorComponentPageFields, FieldValue>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.toSortedMap().entries.forEach { (key, value) ->
            Field(
                field = key,
                value = value,
                onValueChange = { onEdit(key, it) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
