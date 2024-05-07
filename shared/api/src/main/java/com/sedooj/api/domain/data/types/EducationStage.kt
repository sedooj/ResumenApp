package com.sedooj.api.domain.data.types

import androidx.annotation.StringRes
import com.sedooj.ui_kit.R

enum class EducationStage(
    val educationType: EducationType,
    @StringRes
    val title: Int
) {
    COLLEGE(educationType = EducationType.SECONDARY_VOCATIONAL, title = R.string.education_college),
    UNIVERSITY(educationType = EducationType.HIGHER, title = R.string.education_university),
    INSTITUTE(educationType = EducationType.HIGHER, title = R.string.education_institute),
    NOT_SPECIFIED(educationType = EducationType.NOT_SPECIFIED, title = R.string.edu_not_specified)
}