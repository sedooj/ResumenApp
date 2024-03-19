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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val showBottomBar = rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar.value)
                NavigationBarComponent(navController = navController)
        }
    ) {
        SetupNavigation(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            showBottomBar.value = it
        }
    }
}

fun auth(): Boolean {
    return true
}

@Composable
private fun NavigationBarComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    navController: NavController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedPage = backStackEntry?.destination?.route
    val homePagesList = listOf(
        Screens.Home.MAIN,
        Screens.Home.MY_RESUMES,
        Screens.Home.PROFILE,
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
                        navController.navigate(screen.route)
                    }
                },
                icon = {
                    screen.icon()
                }
            )
        }
    }
}
