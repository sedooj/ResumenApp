package com.sedooj.app_ui.pages.resume.create.components.personal.main.data

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
import androidx.compose.material3.FloatingActionButton
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
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.DateValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.GenderConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.HasChildConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.MaritalConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import java.util.Locale

enum class MainPersonalPageFields(
    @StringRes
    val fieldName: Int,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    FIRST_NAME(fieldName = R.string.firstname, readOnly = false),
    SECOND_NAME(fieldName = R.string.secondname, readOnly = false),
    THIRD_NAME(fieldName = R.string.thirdname, readOnly = false),
    DATE_OF_BIRTH(fieldName = R.string.birth_date, readOnly = true),
    CITY(fieldName = R.string.city, readOnly = false),
    RESIDENCE_COUNTRY(
        fieldName = R.string.residence_country,
        readOnly = false
    ),
    GENDER(
        fieldName = R.string.gender_picker,
        suggestions = listOf(
            CustomValue(GenderConvertibleContainer(GenderType.NOT_SELECTED)),
            CustomValue(GenderConvertibleContainer(GenderType.MALE)),
            CustomValue(GenderConvertibleContainer(GenderType.FEMALE))
        ),
        readOnly = true
    ),
    MARITAL(
        fieldName = R.string.marital_picker,
        suggestions = listOf(
            CustomValue(MaritalConvertibleContainer(MaritalStatus.NOT_SELECTED)),
            CustomValue(MaritalConvertibleContainer(MaritalStatus.NOT_MARRIED)),
            CustomValue(MaritalConvertibleContainer(MaritalStatus.MARRIED)),
            CustomValue(MaritalConvertibleContainer(MaritalStatus.FEMALE_NOT_MARRIED)),
            CustomValue(MaritalConvertibleContainer(MaritalStatus.FEMALE_MARRIED))
        ),
        readOnly = true
    ),
    HAS_CHILD(
        fieldName = R.string.children,
        suggestions = listOf(
            CustomValue(HasChildConvertibleContainer(false)),
            CustomValue(HasChildConvertibleContainer(true)),
        ),
        readOnly = true
    )
}

class MainPersonalComponent {

    @Composable
    fun dataMap(initInfo: CreateResumeUseCase.PersonalInformation?): SnapshotStateMap<MainPersonalPageFields, FieldValue> {
        return MainPersonalComponentData().getDataMap(initInfo = initInfo)
    }

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        onValueChange: (MainPersonalPageFields, FieldValue) -> Unit,
        data: Map<MainPersonalPageFields, FieldValue>,
    ) {
        MainPersonalComponentContent().GetContent(
            data = data,
            onValueChange = onValueChange,
            modifier = modifier
        )
    }

    @Composable
    fun GetFAB(
        onSaved: () -> Unit,
        isDataSaved: Boolean, isDataEdited: Boolean,
        data: Map<MainPersonalPageFields, FieldValue>,
        initInfo: CreateResumeUseCase.PersonalInformation?,
        createResumeViewModel: CreateResumeViewModel,
    ) {
        MainPersonalComponentContent().FloatingActionButton(
            onSaved = onSaved,
            isDataSaved = isDataSaved,
            isDataEdited = isDataEdited,
            data = data,
            initInfo = initInfo,
            createResumeViewModel = createResumeViewModel
        )
    }

}

private class MainPersonalComponentData {

    data class EditorMainPersonal(
        var firstName: String,
        var secondName: String,
        var thirdName: String,
        var dateOfBirth: String,
        var city: String,
        var residenceCountry: String,
        var genderType: GenderType,
        var maritalStatus: MaritalStatus,
        var hasChild: Boolean,
    ) : java.io.Serializable

    @Composable
    fun getDataMap(initInfo: CreateResumeUseCase.PersonalInformation?): SnapshotStateMap<MainPersonalPageFields, FieldValue> {
        return remember {
            mutableStateMapOf(
                MainPersonalPageFields.FIRST_NAME to if (initInfo?.firstName != null) TextValue(
                    initInfo.firstName
                ) else TextValue(
                    ""
                ),
                MainPersonalPageFields.SECOND_NAME to if (initInfo?.secondName != null) TextValue(
                    initInfo.secondName
                ) else TextValue(
                    ""
                ),
                MainPersonalPageFields.THIRD_NAME to if (initInfo?.thirdName != null) TextValue(
                    initInfo.thirdName!!
                ) else TextValue(
                    ""
                ),
                MainPersonalPageFields.DATE_OF_BIRTH to if (initInfo?.dateOfBirth != null) DateValue(
                    initInfo.dateOfBirth
                ) else DateValue(
                    ""
                ),
                MainPersonalPageFields.CITY to if (initInfo?.city != null) TextValue(initInfo.city) else TextValue(
                    ""
                ),
                MainPersonalPageFields.RESIDENCE_COUNTRY to if (initInfo?.residenceCountry != null) TextValue(
                    initInfo.residenceCountry
                ) else TextValue(
                    ""
                ),
                MainPersonalPageFields.GENDER to if (initInfo?.genderType != null) CustomValue(
                    GenderConvertibleContainer(initInfo.genderType)
                ) else CustomValue(GenderConvertibleContainer(GenderType.NOT_SELECTED)),
                MainPersonalPageFields.MARITAL to if (initInfo?.maritalStatus != null) CustomValue(
                    MaritalConvertibleContainer(initInfo.maritalStatus)
                ) else CustomValue(
                    MaritalConvertibleContainer(MaritalStatus.NOT_SELECTED)
                ),
                MainPersonalPageFields.HAS_CHILD to if (initInfo?.hasChild != null) CustomValue(
                    HasChildConvertibleContainer(initInfo.hasChild)
                ) else CustomValue(
                    HasChildConvertibleContainer(
                        false
                    )
                )
            )
        }
    }

    @Composable
    fun parseData(
        data: Map<MainPersonalPageFields, FieldValue>,
        initInfo: CreateResumeUseCase.PersonalInformation?,
    ): EditorMainPersonal {
        val firstName =
            data[MainPersonalPageFields.FIRST_NAME]?.asStringValue() ?: initInfo?.firstName
        val secondName =
            data[MainPersonalPageFields.SECOND_NAME]?.asStringValue() ?: initInfo?.secondName
        val thirdName =
            data[MainPersonalPageFields.THIRD_NAME]?.asStringValue() ?: initInfo?.thirdName
        val dateOfBirth =
            data[MainPersonalPageFields.DATE_OF_BIRTH]?.asStringValue() ?: initInfo?.dateOfBirth
        val city = data[MainPersonalPageFields.CITY]?.asStringValue() ?: initInfo?.city
        val residenceCountry = data[MainPersonalPageFields.RESIDENCE_COUNTRY]?.asStringValue()
            ?: initInfo?.residenceCountry
        val genderType =
            (data[MainPersonalPageFields.GENDER]?.asInitialValue() as GenderConvertibleContainer?)?.value
                ?: initInfo?.genderType
        val maritalStatus =
            (data[MainPersonalPageFields.MARITAL]?.asInitialValue() as MaritalConvertibleContainer?)?.value
                ?: initInfo?.maritalStatus
        val hasChild =
            (data[MainPersonalPageFields.HAS_CHILD]?.asInitialValue() as HasChildConvertibleContainer?)?.value
                ?: initInfo?.hasChild

        return EditorMainPersonal(
            firstName = firstName ?: "",
            secondName = secondName ?: "",
            thirdName = thirdName ?: "",
            dateOfBirth = dateOfBirth ?: "",
            city = city ?: "",
            residenceCountry = residenceCountry ?: "",
            genderType = genderType ?: GenderType.NOT_SELECTED,
            maritalStatus = maritalStatus ?: MaritalStatus.NOT_SELECTED,
            hasChild = hasChild ?: false
        )
    }

}

private class MainPersonalComponentContent {

    @Composable
    private fun InputTextField(
        field: MainPersonalPageFields,
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
        field: MainPersonalPageFields,
        value: FieldValue,
        onValueChange: (FieldValue) -> Unit,
        modifier: Modifier = Modifier,
        readOnly: Boolean,
    ) {
        var isFocused by remember { mutableStateOf(false) }
        var showDateDialog by remember { mutableStateOf(false) }
        Column {
            InputTextField(
                field = field,
                value = value.asStringValue(),
                onValueChange = onValueChange,
                modifier = modifier
                    .onFocusChanged {
                    isFocused = it.isFocused && !showDateDialog
                    if (value is DateValue && isFocused) {
                        showDateDialog = true
                    }
                },
                readOnly = readOnly
            )
            if (field.readOnly && value is DateValue) {
                DatePicker(
                    onDismiss = {
                        showDateDialog = false
                        isFocused = false
                    },
                    showBottomSheet = showDateDialog,
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
    fun GetContent(
        data: Map<MainPersonalPageFields, FieldValue>,
        onValueChange: (MainPersonalPageFields, FieldValue) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            data.toSortedMap().forEach { (field, value) ->
                Field(
                    field = field,
                    value = value,
                    onValueChange = { onValueChange(field, it) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = field.readOnly
                )
            }
        }
    }

    @Composable
    fun FloatingActionButton(
        onSaved: () -> Unit,
        isDataSaved: Boolean, isDataEdited: Boolean,
        data: Map<MainPersonalPageFields, FieldValue>,
        initInfo: CreateResumeUseCase.PersonalInformation?,
        createResumeViewModel: CreateResumeViewModel
    ) {
        val parsedData = MainPersonalComponentData().parseData(data = data, initInfo = initInfo)
        AnimatedVisibility(
            visible = !isDataSaved && isDataEdited, enter = scaleIn(tween(200)), exit = scaleOut(
                tween(200)
            )
        ) {
            FloatingActionButton(onClick = {
                createResumeViewModel.updatePersonalInformation(
                    input = CreateResumeUseCase.PersonalInformation(
                        firstName = parsedData.firstName,
                        secondName = parsedData.secondName,
                        thirdName = parsedData.thirdName,
                        dateOfBirth = parsedData.dateOfBirth,
                        city = parsedData.city,
                        residenceCountry = parsedData.residenceCountry,
                        genderType = parsedData.genderType,
                        maritalStatus = parsedData.maritalStatus,
                        education = initInfo?.education ?: emptyList(),
                        hasChild = parsedData.hasChild,
                        email = initInfo?.email ?: "",
                        aboutMe = initInfo?.aboutMe,
                        personalQualities = initInfo?.personalQualities ?: ""

                    )
                )
                onSaved()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.done)
                )
            }
        }
    }


}