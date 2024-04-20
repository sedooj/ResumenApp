package com.sedooj.api.domain.data.types

import com.sedooj.ui_kit.R
enum class PlatformType(val title: String, val icon: Int) {
    WEB("Web", R.drawable.web_development),
    DESKTOP("Desktop", R.drawable.desktop_development),
    MOBILE("Mobile", R.drawable.mobile_development)
}