package com.sedooj.resumen.navigation

enum class NavConfig(val route: String) {
    HOME("HOME"),
    SIGN_UP("SIGN_UP"),
    SIGN_IN("SIGN_IN"),
    AUTHORISATION("AUTH"),
    MAIN("MAIN"),
    MY_RESUMES("MY_RESUMES"),
    PROFILE("PROFILE"),
}

sealed class Screens(val route : String) {
    object Authorisation : Screens(NavConfig.AUTHORISATION.route) {
        object SIGN_IN : Screens(NavConfig.SIGN_IN.route)
        object SIGN_UP : Screens(NavConfig.SIGN_UP.route)
    }
    object Home : Screens(NavConfig.HOME.route) {
        object MAIN : Screens(NavConfig.MAIN.route)
        object MY_RESUMES : Screens(NavConfig.MY_RESUMES.route)
        object PROFILE : Screens(NavConfig.PROFILE.route)
    }
}