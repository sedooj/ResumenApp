package com.sedooj.api.domain.data.types

import androidx.annotation.StringRes
import com.sedooj.ui_kit.R
enum class PlatformType(@StringRes val title: Int, val icon: Int?) {
    WEB(R.string.platform_web, R.drawable.web_development),
    DESKTOP(R.string.platform_desktop, R.drawable.desktop_development),
    MOBILE(R.string.platform_mobile, R.drawable.mobile_development),
    NOT_SELECTED(R.string.field_not_selected, null)
}