package com.sedooj.api.domain.data.types

enum class MaritalStatus(val trancription: String) {
    MARRIED(trancription = "Женат"),
    NOT_MARRIED(trancription = "Не женат"),
    FEMALE_NOT_MARRIED(trancription = "Не замужем"),
    FEMALE_MARRIED(trancription = "Замужем")
}