package com.sedooj.resumen.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.SIGNINDestination
import com.ramcosta.composedestinations.generated.destinations.SIGNUPDestination
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import com.sedooj.resumen.navigation.pages.home.bottomBar.BottomBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination =
        navController.currentDestinationAsState().value ?: NavGraphs.root.startDestination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination != SIGNINDestination && currentDestination != SIGNUPDestination,
                enter = scaleIn(tween(200)),
                exit = scaleOut(tween(200))
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