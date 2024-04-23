package com.sedooj.api.domain.data.types

enum class EducationType(
    val title: String
) {
    SECONDARY("Среднее"),
    SECONDARY_INCOMPLETE("Неоконченное высшее"),
    SECONDARY_VOCATIONAL("Среднее профессиональное"),
    HIGHER("Высшее"),
    HIGHER_INCOMPLETE("Неоконченное высшее"),
    NOT_SPECIFIED("Не указано")
}