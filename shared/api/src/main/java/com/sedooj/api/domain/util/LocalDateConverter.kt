package com.sedooj.api.domain.util

import java.time.LocalDate

fun LocalDate.convert(): kotlinx.datetime.LocalDate {
    return kotlinx.datetime.LocalDate.parse(input = this.toString())
}
