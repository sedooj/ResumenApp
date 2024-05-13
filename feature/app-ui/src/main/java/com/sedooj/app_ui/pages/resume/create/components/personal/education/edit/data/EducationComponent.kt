package com.sedooj.app_ui.pages.resume.create.components.personal.education.edit.data

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
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.DateValue
import com.sedooj.app_ui.pages.resume.create.components.generic.EducationStageConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import java.io.Serializable
import java.util.Locale

enum class EducationComponentEditorPageFields(
    @StringRes
    val fieldName: Int,
    val defaultValue: FieldValue,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    TITLE(
        fieldName = R.string.education_editor_title,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    LocationCity(
        fieldName = R.string.city,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    FACULTY(
        fieldName = R.string.faculty,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    SPECIALITY(
        fieldName = R.string.speciality,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    EDUCATION_STAGE(
        fieldName = R.string.education_stage,
        defaultValue = CustomValue(EducationStageConvertibleContainer(EducationStage.NOT_SPECIFIED)),
        readOnly = true,
        suggestions = listOf(
            CustomValue(EducationStageConvertibleContainer(EducationStage.NOT_SPECIFIED)),
            CustomValue(EducationStageConvertibleContainer(EducationStage.COLLEGE)),
            CustomValue(EducationStageConvertibleContainer(EducationStage.INSTITUTE)),
            CustomValue(EducationStageConvertibleContainer(EducationStage.UNIVERSITY)),
        )
    ),
    ENTER_DATE(
        fieldName = R.string.education_enter_date,
        defaultValue = DateValue(date = ""),
        readOnly = true
    ),
    GRADUATE_DATE(
        fieldName = R.string.education_graduation_date,
        defaultValue = DateValue(date = ""),
        readOnly = true
    ),
}


class EducationComponent {

    @Composable
    fun dataMap(initInfo: EducationComponentData.EditorEducation): SnapshotStateMap<EducationComponentEditorPageFields, FieldValue> {
        return EducationComponentData().rememberDataMap(initInfo = initInfo)
    }

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        onEdit: (EducationComponentEditorPageFields, FieldValue) -> Unit,
        data: Map<EducationComponentEditorPageFields, FieldValue>,
    ) {
        EducationComponentContent().GetContent(onEdit = onEdit, data = data, modifier = modifier)
    }

    @Composable
    fun GetFloatingActionButton(
        onSave: (EducationComponentData.EditorEducation) -> Unit,
        isDataSaved: Boolean, isDataEdited: Boolean,
        data: Map<EducationComponentEditorPageFields, FieldValue>,
        initInfo: EducationComponentData.EditorEducation,
    ) {
        EducationComponentContent().FloatingActionButton(
            onSave = onSave,
            isDataSaved = isDataSaved,
            isDataEdited = isDataEdited,
            data = data,
            initInfo = initInfo
        )
    }

}

class EducationComponentData {

    data class EditorEducation(
        var id: Int,
        var educationStage: EducationStage,
        var title: String,
        var locationCity: String,
        var enterDate: String,
        var graduatedDate: String,
        var faculty: String,
        var speciality: String,
        var isEdit: Boolean = false,
    ) : Serializable

    @Composable
    fun parseData(
        data: Map<EducationComponentEditorPageFields, FieldValue>,
        initInfo: EditorEducation,
    ): EditorEducation {
        val title =
            data[EducationComponentEditorPageFields.TITLE]?.asStringValue() ?: initInfo.title
        val locationCity =
            data[EducationComponentEditorPageFields.LocationCity]?.asStringValue()
                ?: initInfo.locationCity
        val enterDate = data[EducationComponentEditorPageFields.ENTER_DATE]?.asStringValue()
            ?: initInfo.enterDate
        val graduatedDate =
            data[EducationComponentEditorPageFields.GRADUATE_DATE]?.asStringValue()
                ?: initInfo.graduatedDate
        val faculty =
            data[EducationComponentEditorPageFields.FACULTY]?.asStringValue() ?: initInfo.faculty
        val speciality = data[EducationComponentEditorPageFields.SPECIALITY]?.asStringValue()
            ?: initInfo.speciality
        val educationStage =
            (data[EducationComponentEditorPageFields.EDUCATION_STAGE]?.asInitialValue() as EducationStageConvertibleContainer?)?.value
                ?: initInfo.educationStage
        return EditorEducation(
            educationStage = educationStage,
            title = title,
            locationCity = locationCity,
            enterDate = enterDate,
            graduatedDate = graduatedDate,
            faculty = faculty,
            speciality = speciality,
            id = initInfo.id
        )
    }

    @Composable
    fun rememberDataMap(initInfo: EditorEducation): SnapshotStateMap<EducationComponentEditorPageFields, FieldValue> {
        return remember {
            mutableStateMapOf(
                EducationComponentEditorPageFields.TITLE to if (initInfo.title.isEmpty()) TextValue(
                    ""
                ) else TextValue(
                    initInfo.title
                ),
                EducationComponentEditorPageFields.LocationCity to if (initInfo.locationCity.isEmpty()) TextValue(
                    ""
                ) else TextValue(initInfo.locationCity),
                EducationComponentEditorPageFields.FACULTY to if (initInfo.faculty.isBlank()) TextValue(
                    ""
                ) else TextValue(initInfo.faculty),
                EducationComponentEditorPageFields.SPECIALITY to if (initInfo.speciality.isBlank()) TextValue(
                    ""
                ) else TextValue(initInfo.speciality),
                EducationComponentEditorPageFields.EDUCATION_STAGE to
                        if (initInfo.educationStage == EducationStage.NOT_SPECIFIED)
                            CustomValue(
                                EducationStageConvertibleContainer(
                                    EducationStage.NOT_SPECIFIED
                                )
                            ) else
                            CustomValue(
                                EducationStageConvertibleContainer(
                                    initInfo.educationStage
                                )
                            ),
                EducationComponentEditorPageFields.ENTER_DATE to if (initInfo.enterDate.isBlank()) DateValue(
                    ""
                ) else DateValue(initInfo.enterDate),
                EducationComponentEditorPageFields.GRADUATE_DATE to if (initInfo.graduatedDate.isBlank()) DateValue(
                    ""
                ) else DateValue(initInfo.graduatedDate)
            )
        }
    }
}

private class EducationComponentContent {
    @Composable
    fun FloatingActionButton(
        onSave: (EducationComponentData.EditorEducation) -> Unit,
        isDataSaved: Boolean, isDataEdited: Boolean,
        data: Map<EducationComponentEditorPageFields, FieldValue>,
        initInfo: EducationComponentData.EditorEducation,
    ) {
        val parsedData = EducationComponentData().parseData(data = data, initInfo = initInfo)
        AnimatedVisibility(
            visible = !isDataSaved && isDataEdited, enter = scaleIn(tween(200)), exit = scaleOut(
                tween(200)
            )
        ) {
            androidx.compose.material3.FloatingActionButton(onClick = {
                onSave(parsedData)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.done)
                )
            }
        }
    }

    @Composable
    private fun InputTextField(
        field: EducationComponentEditorPageFields,
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
    fun Field(
        field: EducationComponentEditorPageFields,
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
    fun DatePicker(
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
    fun GetContent(
        modifier: Modifier = Modifier,
        onEdit: (EducationComponentEditorPageFields, FieldValue) -> Unit,
        data: Map<EducationComponentEditorPageFields, FieldValue>,
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
}