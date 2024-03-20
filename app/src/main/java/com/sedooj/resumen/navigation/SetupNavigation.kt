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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sedooj.resumen.navigation.pages.SignInPage

@Composable
fun SetupNavigation(
    navController: NavHostController,
    modifier: Modifier,
    onNavigation: (toRoute: String, screenType: Screens.ScreenType) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.Authorisation.route,
    ) {
        navigation(
            startDestination = Screens.Authorisation.SIGN_IN.route,
            route = Screens.Authorisation.route
        ) {
            composable(route = Screens.Authorisation.SIGN_IN.route) {
                SignInPage { toRoute ->
                    onNavigation(toRoute, Screens.ScreenType.HOME)
                }
            }

            composable(route = Screens.Authorisation.SIGN_UP.route) {

            }


            navigation(
                startDestination = Screens.Home.MAIN.route,
                route = Screens.Home.route
            ) {
                composable(route = Screens.Home.MAIN.route) {
                    MainPage { toRoute ->
                        onNavigation(toRoute, Screens.ScreenType.HOME)
                    }
                }
                composable(route = Screens.Home.MY_RESUMES.route) {

                }
                composable(route = Screens.Home.PROFILE.route) {

                }
            }
        }
    }

}


@Composable
fun MainPage(navigateTo: (toRoute: String) -> Unit) {
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
                }
            }
        }
    )
}
