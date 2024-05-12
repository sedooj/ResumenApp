package com.sedooj.app_ui.pages.resume.create.components.vacancy.data

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.ScheduleType
import com.sedooj.api.domain.data.types.StackType
import com.sedooj.app_ui.pages.resume.create.components.generic.BooleanConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.BusynessTypeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.PlatformTypeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.SalaryValue
import com.sedooj.app_ui.pages.resume.create.components.generic.ScheduleTypeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.StackTypeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import com.sedooj.ui_kit.fields.SalaryField

enum class VacancyComponentFields(
    @StringRes
    val fieldName: Int,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    STACK_TYPE(
        fieldName = R.string.stack_picker,
        readOnly = true,
        suggestions = listOf(
            CustomValue(StackTypeConvertibleContainer(StackType.NOT_SELECTED)),
            CustomValue(StackTypeConvertibleContainer(StackType.FRONTEND)),
            CustomValue(StackTypeConvertibleContainer(StackType.BACKEND)),
            CustomValue(StackTypeConvertibleContainer(StackType.FULL_STACK)),
        )
    ),
    PLATFORM_TYPE(
        fieldName = R.string.platform_picker,
        readOnly = true,
        suggestions = listOf(
            CustomValue(PlatformTypeConvertibleContainer(PlatformType.NOT_SELECTED)),
            CustomValue(PlatformTypeConvertibleContainer(PlatformType.MOBILE)),
            CustomValue(PlatformTypeConvertibleContainer(PlatformType.WEB)),
            CustomValue(PlatformTypeConvertibleContainer(PlatformType.DESKTOP)),
        )
    ),
    ROLE(
        fieldName = R.string.desired_role,
        readOnly = false
    ),
    SALARY(
        fieldName = R.string.desired_salary,
        readOnly = false
    ),
    BUSYNESS(
        fieldName = R.string.busyness_picker,
        readOnly = true,
        suggestions = listOf(
            CustomValue(BusynessTypeConvertibleContainer(BusynessType.NOT_SELECTED)),
            CustomValue(BusynessTypeConvertibleContainer(BusynessType.FULL)),
            CustomValue(BusynessTypeConvertibleContainer(BusynessType.PARTIAL)),
            CustomValue(BusynessTypeConvertibleContainer(BusynessType.BY_PROJECT)),
            CustomValue(BusynessTypeConvertibleContainer(BusynessType.VOLUNTEERING)),
            CustomValue(BusynessTypeConvertibleContainer(BusynessType.INTERNSHIP)),
        )
    ),
    SCHEDULE(
        fieldName = R.string.schedule_picker,
        readOnly = true,
        suggestions = listOf(
            CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.NOT_SELECTED)),
            CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.FLEXIBLE_SCHEDULE)),
            CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.SHIFT_SCHEDULE)),
            CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.FULL_DAY)),
            CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.REMOTE_WORK)),
            CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.SHIFT_METHOD)),
        )
    ),
    READY_FOR_TRAVELLING(
        fieldName = R.string.ready_for_travelling,
        readOnly = true,
        suggestions = listOf(
            CustomValue(BooleanConvertibleContainer(false)),
            CustomValue(BooleanConvertibleContainer(true)),
        )
    )
}

class VacancyComponent {

    @Composable
    fun dataMap(initInfo: CreateResumeUseCase.VacancyInformation?): SnapshotStateMap<VacancyComponentFields, FieldValue> {
        return VacancyComponentData().rememberDataMap(initInfo = initInfo)
    }

    data class EditorVacancy(
        var stackType: StackType,
        var platformType: PlatformType,
        var desiredRole: String,
        var desiredSalaryFrom: String,
        var desiredSalaryTo: String,
        var busynessType: BusynessType,
        var scheduleType: ScheduleType,
        var readyForTravelling: Boolean,
    ) : java.io.Serializable

    @Composable
    fun parseData(
        data: Map<VacancyComponentFields, FieldValue>,
        initInfo: CreateResumeUseCase.VacancyInformation?,
    ): EditorVacancy {
        val stackType =
            (data[VacancyComponentFields.STACK_TYPE]?.asInitialValue() as StackTypeConvertibleContainer?)?.value
                ?: initInfo?.stackType
        val platformType =
            (data[VacancyComponentFields.PLATFORM_TYPE]?.asInitialValue() as PlatformTypeConvertibleContainer?)?.value
                ?: initInfo?.platformType
        val desiredRole = data[VacancyComponentFields.ROLE]?.asStringValue()
            ?: initInfo?.desiredRole
        val desiredSalaryFrom =
            (data[VacancyComponentFields.SALARY]?.asInitialValue() as SalaryValue?)?.from
                ?: initInfo?.desiredSalaryFrom
        val desiredSalaryTo =
            (data[VacancyComponentFields.SALARY]?.asInitialValue() as SalaryValue?)?.to
                ?: initInfo?.desiredSalaryTo
        val busynessType =
            (data[VacancyComponentFields.BUSYNESS]?.asInitialValue() as BusynessTypeConvertibleContainer?)?.value
                ?: initInfo?.busynessType
        val scheduleType =
            (data[VacancyComponentFields.SCHEDULE]?.asInitialValue() as ScheduleTypeConvertibleContainer?)?.value
                ?: initInfo?.scheduleType
        val readyForTravelling =
            (data[VacancyComponentFields.READY_FOR_TRAVELLING]?.asInitialValue() as BooleanConvertibleContainer?)?.value
                ?: initInfo?.readyForTravelling
        return EditorVacancy(
            stackType = stackType ?: StackType.NOT_SELECTED,
            platformType = platformType ?: PlatformType.NOT_SELECTED,
            desiredRole = desiredRole ?: "",
            desiredSalaryFrom = desiredSalaryFrom ?: "",
            desiredSalaryTo = desiredSalaryTo ?: "",
            busynessType = busynessType ?: BusynessType.NOT_SELECTED,
            scheduleType = scheduleType ?: ScheduleType.NOT_SELECTED,
            readyForTravelling = readyForTravelling ?: false
        )
    }

    @Composable
    fun LostDataAlert(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        AlertDialog(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = stringResource(
                        id = R.string.warning
                    ),
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = stringResource(id = R.string.changes_is_not_saved),
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = {
                onDismiss()
            },
            dismissButton = {
                FilledButton(
                    label = stringResource(id = R.string.quit),
                    onClick = { onConfirm() },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    )
                )
            },
            confirmButton = {
                FilledButton(
                    label = stringResource(id = R.string.continue_edit),
                    onClick = { onDismiss() },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    )
                )
            },
            modifier = modifier
        )
    }

    @Composable
    fun content(
        data: Map<VacancyComponentFields, FieldValue>,
        onValueChange: (VacancyComponentFields, FieldValue) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        VacancyComponentContent().content(
            data = data,
            onValueChange = onValueChange,
            modifier = modifier
        )
    }
}

class VacancyComponentContent {

    @Composable
    private fun InputTextField(
        field: VacancyComponentFields,
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
        field: VacancyComponentFields,
        value: FieldValue,
        onValueChange: (FieldValue) -> Unit,
        modifier: Modifier = Modifier,
        readOnly: Boolean,
    ) {
        var isFocused by remember { mutableStateOf(false) }
        Column {
            if (value is SalaryValue) {
                SalaryField(onFromValueChange = {
                    onValueChange(SalaryValue(from = it, to = value.to))
                }, onToValueChange = {
                    onValueChange(SalaryValue(from = value.from, to = it))
                }, fromValue = value.from, toValue = value.to)
            } else {
                InputTextField(
                    field = field,
                    value = value.asStringValue(),
                    onValueChange = onValueChange,
                    modifier = modifier.onFocusChanged { isFocused = it.isFocused },
                    readOnly = readOnly
                )
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
    }


    @Composable
    fun content(
        data: Map<VacancyComponentFields, FieldValue>,
        onValueChange: (VacancyComponentFields, FieldValue) -> Unit,
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

}

class VacancyComponentData {
    @Composable
    fun rememberDataMap(initInfo: CreateResumeUseCase.VacancyInformation?): SnapshotStateMap<VacancyComponentFields, FieldValue> {
        return remember {
            mutableStateMapOf(
                VacancyComponentFields.STACK_TYPE to if (initInfo?.stackType == null) CustomValue(
                    StackTypeConvertibleContainer(StackType.NOT_SELECTED)
                ) else CustomValue(StackTypeConvertibleContainer(initInfo.stackType)),

                VacancyComponentFields.PLATFORM_TYPE to if (initInfo?.platformType == null)
                    CustomValue(PlatformTypeConvertibleContainer(PlatformType.NOT_SELECTED))
                else CustomValue(PlatformTypeConvertibleContainer(initInfo.platformType)),

                VacancyComponentFields.ROLE to if (initInfo?.desiredRole == null)
                    TextValue("")
                else TextValue(initInfo.desiredRole),

                VacancyComponentFields.SALARY to if (initInfo?.desiredSalaryFrom == null && initInfo?.desiredSalaryTo == null)
                    SalaryValue(from = "", to = "")
                else SalaryValue(from = initInfo.desiredSalaryFrom, to = initInfo.desiredSalaryTo),

                VacancyComponentFields.BUSYNESS to if (initInfo?.busynessType == null)
                    CustomValue(BusynessTypeConvertibleContainer(BusynessType.NOT_SELECTED))
                else
                    CustomValue(BusynessTypeConvertibleContainer(initInfo.busynessType)),

                VacancyComponentFields.SCHEDULE to if (initInfo?.scheduleType == null)
                    CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.NOT_SELECTED))
                else
                    CustomValue(ScheduleTypeConvertibleContainer(initInfo.scheduleType)),

                VacancyComponentFields.READY_FOR_TRAVELLING to CustomValue(
                    BooleanConvertibleContainer(initInfo?.readyForTravelling ?: false)
                )
            )
        }
    }
}