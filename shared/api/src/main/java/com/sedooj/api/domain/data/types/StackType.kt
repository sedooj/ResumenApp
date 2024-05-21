package com.sedooj.api.domain.data.types

import androidx.annotation.StringRes
import com.sedooj.ui_kit.R

enum class StackType(@StringRes val title: Int, val icon: Int?) {
    FRONTEND(R.string.frontend, R.drawable.code_brackets),
    BACKEND(R.string.backend, R.drawable.backend),
    FULL_STACK(R.string.full_stack, R.drawable.stack),
    NOT_SELECTED(R.string.field_not_selected, null)
}