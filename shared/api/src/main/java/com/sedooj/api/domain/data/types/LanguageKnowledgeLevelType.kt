package com.sedooj.api.domain.data.types

import androidx.annotation.StringRes
import com.sedooj.ui_kit.R

enum class LanguageKnowledgeLevelType(
    @StringRes
    val title: Int,
) {
    A1(R.string.language_knowledge_a1),
    A2(R.string.language_knowledge_a2),
    B1(R.string.language_knowledge_b1),
    B2(R.string.language_knowledge_b2),
    C1(R.string.language_knowledge_c1),
    C2(R.string.language_knowledge_c2),
    NOT_SELECTED(R.string.field_not_selected)
}