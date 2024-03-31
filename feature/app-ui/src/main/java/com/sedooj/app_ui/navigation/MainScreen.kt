package com.sedooj.app_ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.HOMEDestination
import com.ramcosta.composedestinations.generated.destinations.MYRESUMESDestination
import com.ramcosta.composedestinations.generated.destinations.PROFILEDestination
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import com.sedooj.app_ui.pages.home.bottomBar.BottomBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination =
        navController.currentDestinationAsState().value ?: NavGraphs.root.startDestination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination == HOMEDestination || currentDestination == MYRESUMESDestination || currentDestination == PROFILEDestination,
                enter = slideInVertically(tween(200)) { it },
                exit = slideOutVertically(tween(200)) { it }
            ) {
                BottomBar(navController = navController)
            }
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            modifier = Modifier.padding(it.apply { 10.dp })
        )
    }

}