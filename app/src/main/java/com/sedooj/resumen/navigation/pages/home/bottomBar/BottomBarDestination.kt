package com.sedooj.resumen.navigation.pages.home.bottomBar

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.generated.destinations.HOMEDestination
import com.ramcosta.composedestinations.generated.destinations.MYRESUMESDestination
import com.ramcosta.composedestinations.generated.destinations.PROFILEDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.sedooj.resumen.R

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {
    HOME(direction = HOMEDestination, icon = R.drawable.home, R.string.home),
    MY_RESUMES(direction = MYRESUMESDestination, icon = R.drawable.my_resumes, R.string.my_resumes),
    PROFILE(direction = PROFILEDestination, icon = R.drawable.profile, R.string.profile),
}