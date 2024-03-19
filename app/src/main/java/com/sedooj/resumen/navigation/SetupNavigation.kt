package com.sedooj.resumen.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.sedooj.resumen.navigation.pages.SignInPage

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Authorisation.route) {
        navigation(
            startDestination = Screens.Authorisation.SIGN_IN.route,
            route = Screens.Authorisation.route
        ) {
            composable(route = Screens.Authorisation.SIGN_IN.route) {
                SignInPage {
                    navController.navigate(
                        it, NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            }
            composable(route = Screens.Authorisation.SIGN_UP.route) {
                SignUpPage {
                    navController.navigate(
                        it, NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            }
        }
        navigation(
            startDestination = Screens.Home.MAIN.route,
            route = Screens.Home.route
        ) {

            composable(route = Screens.Home.MAIN.route) {
                MainPage {
                    navController.navigate(
                        it, NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            }
            composable(route = Screens.Home.MY_RESUMES.route) {
                ResumesPage {
                    navController.navigate(
                        it, NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            }
            composable(route = Screens.Home.PROFILE.route) {
                ProfilePage {
                    navController.navigate(
                        it, NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            }
        }
    }
}



@Composable
fun HostRouteChanger(
    route: Int,
    navigateTo: (route: String) -> Unit
) {
    when (route) {
        0 -> {
            OutlinedButton(onClick = { navigateTo(Screens.Authorisation.route) }) {
                Text("To auth")
            }
        }

        1 -> {
            OutlinedButton(onClick = { navigateTo(Screens.Home.route) }) {
                Text("To home")
            }
        }

        else ->
            return
    }
}

@Composable
fun MainPage(navigateTo: (route: String) -> Unit) {
    Scaffold(
        topBar = {
            val counter = rememberSaveable { mutableStateOf(Math.random()) }
            Text(Screens.Home.MAIN.route + " ${counter.value}")
        },
        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                Column {

                    OutlinedButton(
                        onClick = {
                            navigateTo(Screens.Home.MY_RESUMES.route)
                        }) {
                        Text(text = "To ${Screens.Home.MY_RESUMES.route}")
                    }
                    OutlinedButton(
                        onClick = {
                            navigateTo(Screens.Home.PROFILE.route)
                        }) {
                        Text(text = "To ${Screens.Home.PROFILE.route}")
                    }
                    HostRouteChanger(route = 0) {
                        navigateTo(it)
                    }
                }
            }
        }
    )
}

@Composable
fun ResumesPage(navigateTo: (route: String) -> Unit) {
    Scaffold(
        topBar = {
            Text(Screens.Home.MY_RESUMES.route)
        },
        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                Column {

                    OutlinedButton(
                        onClick = {
                            navigateTo(Screens.Home.MAIN.route)
                        }) {
                        Text(text = "To ${Screens.Home.MAIN.route}")
                    }
                    OutlinedButton(
                        onClick = {
                            navigateTo(Screens.Home.PROFILE.route)
                        }) {
                        Text(text = "To ${Screens.Home.PROFILE.route}")
                    }
                    HostRouteChanger(route = 0) {
                        navigateTo(it)
                    }
                }
            }
        }
    )
}

@Composable
fun ProfilePage(navigateTo: (route: String) -> Unit) {
    Scaffold(
        topBar = {
            Text(Screens.Home.PROFILE.route)
        },
        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                Column {

                    OutlinedButton(
                        onClick = {
                            navigateTo(Screens.Home.MAIN.route)
                        }) {
                        Text(text = "To ${Screens.Home.MAIN.route}")
                    }
                    OutlinedButton(
                        onClick = {
                            navigateTo(Screens.Home.MY_RESUMES.route)
                        }) {
                        Text(text = "To ${Screens.Home.MY_RESUMES.route}")
                    }
                    HostRouteChanger(route = 0) {
                        navigateTo(it)
                    }
                }
            }
        }
    )
}

@Composable
fun SignUpPage(navigateTo: (route: String) -> Unit) {
    Scaffold(
        topBar = {
            Text("${NavConfig.SIGN_UP}")
        },
        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                Column {
                    OutlinedButton(onClick = { navigateTo(Screens.Authorisation.SIGN_IN.route) }) {
                        Text(text = "to sign in")
                    }
                    HostRouteChanger(route = 1) {
                        navigateTo(it)
                    }
                }
            }
        }
    )
}