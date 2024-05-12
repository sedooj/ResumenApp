package com.sedooj.app_ui.pages.resume.create.components.generic

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.SocialMedia
import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.ScheduleType
import com.sedooj.api.domain.data.types.StackType
import com.sedooj.ui_kit.R

sealed class FieldValue

class TextValue(val text: String) : FieldValue()
class DateValue(val date: String) : FieldValue()
class SalaryValue(val from: String, val to: String) : FieldValue()

class CustomValue<T : ConvertibleValue>(val value: T) : FieldValue()

class ConvertibleStringResValue(
    private val value: Int,
) : ConvertibleValue {

    @Composable
    override fun asStringValue(): String {
        return stringResource(id = value)
    }
}

interface ConvertibleValue {

    @Composable
    fun asStringValue(): String

}

interface ConvertibleEducationValue {

    fun asMutableEducationList(): MutableList<Education>

}

@Composable
fun FieldValue.asStringValue(): String {
    return when (this) {
        is CustomValue<*> -> value.asStringValue()
        is TextValue -> text
        is DateValue -> date
        is SalaryValue -> "${stringResource(id = R.string.from)} $from ${stringResource(id = R.string.to)} $to"
    }
}

fun FieldValue.asInitialValue(): Any {
    return when (this) {
        is CustomValue<*> -> value
        is DateValue -> date
        is TextValue -> text
        is SalaryValue -> SalaryValue(from = from, to = to)
    }
}

@JvmInline
value class StackTypeConvertibleContainer(val value: StackType) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return when (value) {
            StackType.FRONTEND -> stringResource(id = value.title)
            StackType.BACKEND -> stringResource(id = value.title)
            StackType.FULL_STACK -> stringResource(id = value.title)
            StackType.NOT_SELECTED -> stringResource(id = value.title)
        }
    }

}

@JvmInline
value class BooleanConvertibleContainer(val value: Boolean) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return when (value) {
            true -> stringResource(id = R.string.yes)
            false -> stringResource(id = R.string.no)
        }
    }

}

@JvmInline
value class PlatformTypeConvertibleContainer(val value: PlatformType) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return when (value) {
            PlatformType.WEB -> stringResource(id = value.title)
            PlatformType.DESKTOP -> stringResource(id = value.title)
            PlatformType.MOBILE -> stringResource(id = value.title)
            PlatformType.NOT_SELECTED -> stringResource(id = value.title)
        }
    }

}

@JvmInline
value class BusynessTypeConvertibleContainer(val value: BusynessType) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return when (value) {
            BusynessType.FULL -> stringResource(id = value.title)
            BusynessType.PARTIAL -> stringResource(id = value.title)
            BusynessType.BY_PROJECT -> stringResource(id = value.title)
            BusynessType.INTERNSHIP -> stringResource(id = value.title)
            BusynessType.VOLUNTEERING -> stringResource(id = value.title)
            BusynessType.NOT_SELECTED -> stringResource(id = value.title)
        }
    }

}

@JvmInline
value class ScheduleTypeConvertibleContainer(val value: ScheduleType) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return when (value) {
            ScheduleType.FULL_DAY -> stringResource(id = value.title)
            ScheduleType.SHIFT_SCHEDULE -> stringResource(id = value.title)
            ScheduleType.FLEXIBLE_SCHEDULE -> stringResource(id = value.title)
            ScheduleType.REMOTE_WORK -> stringResource(id = value.title)
            ScheduleType.SHIFT_METHOD -> stringResource(id = value.title)
            ScheduleType.NOT_SELECTED -> stringResource(id = value.title)
        }
    }

}

@JvmInline
value class EducationStageConvertibleContainer(val value: EducationStage) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return when (value) {
            EducationStage.COLLEGE -> stringResource(id = value.title)
            EducationStage.UNIVERSITY -> stringResource(id = value.title)
            EducationStage.INSTITUTE -> stringResource(id = value.title)
            EducationStage.NOT_SPECIFIED -> stringResource(id = value.title)
        }
    }

}

@JvmInline
value class GenderConvertibleContainer(val value: GenderType) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return stringResource(id = value.title)
    }

}

@JvmInline
value class MaritalConvertibleContainer(val value: MaritalStatus) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return stringResource(id = value.title)
    }

}

@JvmInline
value class EducationConvertibleContainer(val value: List<Education>) : ConvertibleValue,
    ConvertibleEducationValue {

    @Composable
    override fun asStringValue(): String {
        TODO("Not yet implemented")
    }

    override fun asMutableEducationList(): MutableList<Education> {
        return value.map {
            Education(
                educationStage = it.educationStage,
                title = it.title,
                locationCity = it.locationCity,
                enterDate = it.enterDate,
                graduatedDate = it.graduatedDate,
                faculty = it.faculty,
                speciality = it.speciality
            )
        }.toMutableList()
    }
}

@JvmInline
value class HasChildConvertibleContainer(val value: Boolean) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return if (value) stringResource(id = R.string.has_children_yes) else stringResource(id = R.string.has_children_no)
    }

}

@JvmInline
value class SocialMediaConvertibleContainer(val value: List<SocialMedia>) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return "tododo" // TODO()
    }
}