package com.sedooj.api.domain.data.types

import com.sedooj.ui_kit.R

enum class MaritalStatus(val title: Int, val genderType: GenderType) {
    MARRIED(title = R.string.married, GenderType.MALE),
    NOT_MARRIED(title = R.string.not_married, GenderType.MALE),
    FEMALE_NOT_MARRIED(title = R.string.female_not_married, GenderType.FEMALE),
    FEMALE_MARRIED(title = R.string.female_married, GenderType.FEMALE)
}