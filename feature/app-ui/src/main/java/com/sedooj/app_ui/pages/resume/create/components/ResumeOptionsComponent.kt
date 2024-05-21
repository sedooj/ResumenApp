package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.runtime.Composable
import com.sedooj.ui_kit.fields.NotNullableValueLatinTextField
import com.sedooj.ui_kit.R

@Composable
fun ResumeOptionsComponent(
    titleValue: String?,
    onValueChange: (String) -> Unit,
) {
    NotNullableValueLatinTextField(
        label = R.string.resume_name,
        onValueChange = {
            onValueChange(it)
        },
        value = titleValue
    )
}

