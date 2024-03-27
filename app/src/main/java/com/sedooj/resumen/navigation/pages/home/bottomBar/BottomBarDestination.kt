package com.sedooj.resumen.navigation.pages.home.bottomBar

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.generated.destinations.HOMEDestination
import com.ramcosta.composedestinations.generated.destinations.MYRESUMESDestination
import com.ramcosta.composedestinations.generated.destinations.PROFILEDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.sedooj.resumen.R
import com.sedooj.resumen.navigation.pages.Routes

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int,
    val route: String
) {
    HOME(direction = HOMEDestination, icon = R.drawable.home, R.string.home, Routes.HOME),
    MY_RESUMES(direction = MYRESUMESDestination, icon = R.drawable.my_resumes, R.string.my_resumes, Routes.MY_RESUMES),
    PROFILE(direction = PROFILEDestination, icon = R.drawable.profile, R.string.profile, Routes.PROFILE),
}