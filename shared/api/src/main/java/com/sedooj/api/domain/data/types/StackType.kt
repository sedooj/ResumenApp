package com.sedooj.api.domain.data.types

import com.sedooj.ui_kit.R

enum class StackType(val title: String, val icon: Int) {
    FRONTEND("Frontend", R.drawable.code_brackets),
    BACKEND("Backend", R.drawable.backend),
    FULL_STACK("Full stack", R.drawable.stack)
}