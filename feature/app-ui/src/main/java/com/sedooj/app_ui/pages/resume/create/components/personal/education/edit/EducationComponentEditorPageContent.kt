package com.sedooj.app_ui.pages.resume.create.components.personal.education.edit

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.dropUnlessResumed
import com.ramcosta.composedestinations.generated.destinations.EDUCATIONEDITORDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.pages.resume.create.components.generic.DateValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import com.sedooj.ui_kit.R
import kotlinx.coroutines.launch
import java.util.Locale

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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Field(
    field: EducationComponentEditorPageFields,
    value: FieldValue,
    onValueChange: (FieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    Column {
        InputTextField(
            field = field,
            value = value.asStringValue(),
            onValueChange = onValueChange,
            modifier = modifier.onFocusChanged {
                isFocused = it.isFocused && !showBottomSheet
                if (field.readOnly && value is DateValue && it.isFocused) {
                    showBottomSheet = true
                }
            },
            readOnly = field.readOnly
        )
        if (field.readOnly && value is DateValue) {
            DatePicker(
                onDismiss = {
                    showBottomSheet = false
                    isFocused = false
                },
                showBottomSheet = showBottomSheet,
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
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
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
    showBottomSheet: Boolean,
    content: @Composable () -> Unit,
) {

    if (showBottomSheet)
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
fun EducationComponentEditorPageContent(
    modifier: Modifier = Modifier,
    onEdit: (EducationComponentEditorPageFields, FieldValue) -> Unit,
    data: Map<EducationComponentEditorPageFields, FieldValue>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
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


@Composable
fun EducationEditorPageContent(
    educationList: List<CreateResumeUseCase.PersonalInformation.Education>?,
    onEdit: (Int, CreateResumeUseCase.PersonalInformation.Education) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (educationList.isNullOrEmpty())
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
            Text(
                text = stringResource(id = R.string.put_information_about_education),
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        })
    else
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            educationList.forEachIndexed { index, education ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = education.title,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }, supportingContent = {

                    }, leadingContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.education),
                            contentDescription = education.title,
                            modifier = Modifier.size(40.dp)
                        )
                    }, modifier = modifier.clickable(onClick = dropUnlessResumed {
                        onEdit(
                            index, education
                        )
                    })
                )
            }
        }
}

fun createOrEdit(
    navigator: DestinationsNavigator,
    id: Int,
    education: CreateResumeUseCase.PersonalInformation.Education? = null,
) {
    if (education == null)
        navigator.navigate(
            EDUCATIONEDITORDestination(
                EditorEducation(
                    id = id,
                    educationStage = EducationStage.NOT_SPECIFIED,
                    title = "",
                    locationCity = "",
                    enterDate = "",
                    graduatedDate = "",
                    faculty = "",
                    speciality = ""
                )
            )
        ) {
            launchSingleTop = true
        }
    else
        navigator.navigate(
            EDUCATIONEDITORDestination(
                EditorEducation(
                    id = id,
                    educationStage = education.educationStage,
                    title = education.title,
                    locationCity = education.locationCity,
                    enterDate = education.enterDate,
                    graduatedDate = education.graduatedDate,
                    faculty = education.faculty,
                    speciality = education.speciality,
                    isEdit = true
                )
            )
        ) {
            launchSingleTop = true
        }
}