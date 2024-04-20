package com.sedooj.api.domain.data.types

import com.sedooj.ui_kit.R

enum class ScheduleType(val title: Int) {
    FULL_DAY(R.string.schedule_full_day),
    SHIFT_SCHEDULE(R.string.schedule_flexible_schedule),
    FLEXIBLE_SCHEDULE(R.string.schedule_flexible_schedule),
    REMOTE_WORK(R.string.schedule_remote),
    SHIFT_METHOD(R.string.schedule_shift_method)
}