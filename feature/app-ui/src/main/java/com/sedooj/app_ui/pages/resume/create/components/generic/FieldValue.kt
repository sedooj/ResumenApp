package com.sedooj.app_ui.pages.resume.create.components.generic

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.ui_kit.R

sealed class FieldValue

class TextValue(val text: String) : FieldValue()

class CustomValue<T : ConvertibleValue>(val value: T) : FieldValue()

class ConvertibleStringResValue(
    private val value: Int
): ConvertibleValue {

    @Composable
    override fun asStringValue(): String {
        return stringResource(id = value)
    }

}

interface ConvertibleValue {

    @Composable
    fun asStringValue(): String

}

@Composable
fun FieldValue.asStringValue(): String {
    return when (this) {
        is CustomValue<*> -> value.asStringValue()
        is TextValue -> text
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
value class EducationConvertibleContainer(val value: List<CreateResumeUseCase.PersonalInformation.Education>) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return "todo" // TODO()
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
value class SocialMediaConvertibleContainer(val value: List<CreateResumeUseCase.PersonalInformation.SocialMedia>) : ConvertibleValue {
    @Composable
    override fun asStringValue(): String {
        return "tododo"
    }
}