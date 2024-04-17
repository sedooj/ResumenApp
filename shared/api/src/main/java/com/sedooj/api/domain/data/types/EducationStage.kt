package com.sedooj.api.domain.data.types

enum class EducationStage(
    val educationType: EducationType
) {
    COLLEGE(educationType = EducationType.SECONDARY_VOCATIONAL),
    UNIVERSITY(educationType = EducationType.HIGHER),
    INSTITUTE(educationType = EducationType.HIGHER)
}