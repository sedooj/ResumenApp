package com.sedooj.api.domain.data.types

import androidx.annotation.StringRes
import com.sedooj.ui_kit.R

enum class BusynessType(@StringRes val title: Int) {
    FULL(R.string.busyness_full),
    PARTIAL(R.string.busyness_partial),
    BY_PROJECT(R.string.busyness_by_project),
    INTERNSHIP(R.string.busyness_by_internship),
    VOLUNTEERING(R.string.busyness_by_volunteering),
    NOT_SELECTED(R.string.field_not_selected)
}