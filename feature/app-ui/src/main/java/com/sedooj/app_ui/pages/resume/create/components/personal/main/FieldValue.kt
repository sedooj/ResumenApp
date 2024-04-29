package com.sedooj.app_ui.pages.resume.create.components.personal.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

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