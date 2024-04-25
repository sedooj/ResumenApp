package com.sedooj.app_ui.pages.home.bottomBar

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.generated.destinations.HOMEDestination
import com.ramcosta.composedestinations.generated.destinations.MYRESUMESDestination
import com.ramcosta.composedestinations.generated.destinations.PROFILEDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.sedooj.arch.Routes
import com.sedooj.ui_kit.R.drawable
import com.sedooj.ui_kit.R.string

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int,
    val route: String,
) {
    HOME(direction = HOMEDestination, icon = drawable.home, string.home, Routes.HOME),
    MY_RESUMES(
        direction = MYRESUMESDestination,
        icon = drawable.my_resumes,
        string.my_resumes,
        Routes.MY_RESUMES
    ),
    PROFILE(
        direction = PROFILEDestination,
        icon = drawable.profile,
        string.profile,
        Routes.PROFILE
    ),
}