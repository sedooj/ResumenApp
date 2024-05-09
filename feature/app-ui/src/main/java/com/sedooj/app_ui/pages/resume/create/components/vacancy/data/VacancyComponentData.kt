package com.sedooj.app_ui.pages.resume.create.components.vacancy.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
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
import com.sedooj.app_ui.pages.resume.create.components.generic.ScheduleTypeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.StackTypeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.ui_kit.R

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

class VacancyComponentData {
    @Composable
    fun rememberDataMap(initInfo: CreateResumeUseCase.VacancyInformation?): SnapshotStateMap<VacancyComponentFields, FieldValue> {
        return remember {
            mutableStateMapOf(
                VacancyComponentFields.STACK_TYPE to if (initInfo?.stackType == StackType.NOT_SELECTED) CustomValue(
                    StackTypeConvertibleContainer(StackType.NOT_SELECTED)
                ) else CustomValue(StackTypeConvertibleContainer(initInfo!!.stackType)),

                VacancyComponentFields.PLATFORM_TYPE to if (initInfo.platformType == PlatformType.NOT_SELECTED)
                    CustomValue(PlatformTypeConvertibleContainer(PlatformType.NOT_SELECTED))
                else CustomValue(PlatformTypeConvertibleContainer(initInfo.platformType)),

                VacancyComponentFields.ROLE to if (initInfo.desiredRole.isEmpty())
                    TextValue("")
                else TextValue(initInfo.desiredRole),

                VacancyComponentFields.SALARY to if (initInfo.desiredSalary.isNullOrEmpty())
                    TextValue("")
                else TextValue(initInfo.desiredSalary ?: "Null"),

                VacancyComponentFields.BUSYNESS to if (initInfo.busynessType == BusynessType.NOT_SELECTED)
                    CustomValue(BusynessTypeConvertibleContainer(BusynessType.NOT_SELECTED))
                else
                    CustomValue(BusynessTypeConvertibleContainer(initInfo.busynessType!!)),

                VacancyComponentFields.SCHEDULE to if (initInfo.scheduleType == ScheduleType.NOT_SELECTED)
                    CustomValue(ScheduleTypeConvertibleContainer(ScheduleType.NOT_SELECTED))
                else
                    CustomValue(ScheduleTypeConvertibleContainer(initInfo.scheduleType)),

                VacancyComponentFields.READY_FOR_TRAVELLING to CustomValue(
                    BooleanConvertibleContainer(initInfo.readyForTravelling)
                )
            )
        }
    }
}