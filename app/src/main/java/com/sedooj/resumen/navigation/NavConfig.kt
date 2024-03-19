package com.sedooj.resumen.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sedooj.resumen.R

enum class NavConfig(val route: String) {
    HOME("HOME"),
    SIGN_UP("SIGN_UP"),
    SIGN_IN("SIGN_IN"),
    AUTHORISATION("AUTH"),
    MAIN("MAIN"),
    MY_RESUMES("MY_RESUMES"),
    PROFILE("PROFILE"),
}

sealed class Screens(
    val route: String,
    val transcription: String,
    val icon: @Composable () -> Unit = {}
) {
    object Authorisation : Screens(NavConfig.AUTHORISATION.route, transcription = "Авторизация") {
        object SIGN_IN : Screens(NavConfig.SIGN_IN.route, transcription = "Войти")
        object SIGN_UP : Screens(NavConfig.SIGN_UP.route, transcription = "Зарегистрироваться")
    }

    object Home : Screens(NavConfig.HOME.route, "Главная") {
        object MAIN : Screens(
            NavConfig.MAIN.route,
            transcription = "Главная",
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home page"
                )
            })

        object MY_RESUMES : Screens(NavConfig.MY_RESUMES.route, transcription = "Мои резюме")
        object PROFILE : Screens(NavConfig.PROFILE.route, transcription = "Профиль")
    }
}