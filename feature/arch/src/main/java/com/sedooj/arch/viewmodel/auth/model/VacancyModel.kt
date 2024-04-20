package com.sedooj.arch.viewmodel.auth.model

import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.ScheduleType
import com.sedooj.api.domain.data.types.StackType

interface VacancyModel {

    fun updateStackType(stackType: StackType)

    fun updatePlatformType(platformType: PlatformType)

    fun updateDesiredRole(desiredRole: String)
    fun updateDesiredSalary(desiredRole: String?)
    fun updateBusynessType(busynessType: BusynessType?)
    fun updateScheduleType(scheduleType: ScheduleType)
    fun updateReadyForTravelling(ready: Boolean)
}