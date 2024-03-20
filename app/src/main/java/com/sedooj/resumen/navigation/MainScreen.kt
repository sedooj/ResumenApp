package com.sedooj.resumen.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val screenType = rememberSaveable {
        mutableStateOf(Screens.ScreenType.AUTH)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (screenType.value == Screens.ScreenType.HOME)
                NavigationBarComponent(
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController
                ) { toRoute, type ->
                    navController.navigate(toRoute)
                    screenType.value = type
                }
        }
    ) {
        SetupNavigation(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { route, type ->
            if (screenType.value == Screens.ScreenType.AUTH) {
                // TODO(pop up to login)
                navController.navigate(
                    route, NavOptions
                        .Builder()
                        .setLaunchSingleTop(true)
                        .build())

            } else {
                navController.navigate(
                    route
                ) {
                    this.launchSingleTop = true
                }
            }
            screenType.value = type


        }
    }
}

fun auth(): Boolean {
    return true
}

@Composable
private fun NavigationBarComponent(
    modifier: Modifier,
    navController: NavController,
    onClick: (route: String, screenType: Screens.ScreenType) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedPage = backStackEntry?.destination?.route
    val homePagesList = listOf(
        Screens.Home.MAIN,
        Screens.Home.MY_RESUMES,
        Screens.Home.PROFILE
    )
    NavigationBar(
        modifier = modifier
    ) {
        homePagesList.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.transcription)
                },
                selected = selectedPage == screen.route,
                onClick = {
                    if (selectedPage != screen.route) {
                        onClick(screen.route, screen.screenType)
                    }
                },
                icon = {
                    screen.icon()
                }
            )
        }
    }
}
