package com.sedooj.app_ui.pages.home.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.ExperimentalSafeArgsApi
import androidx.navigation.NavController
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.HomeDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileDestination
import com.ramcosta.composedestinations.generated.destinations.ResumeListDestination
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import kotlinx.coroutines.launch

@Composable
fun AnimatedBottomBar(
    navController: NavController,
) {
    val currentDestination =
        navController.currentDestinationAsState().value ?: NavGraphs.root.startDestination
    AnimatedVisibility(
        visible = currentDestination == HomeDestination || currentDestination == ResumeListDestination || currentDestination == ProfileDestination,
        enter = slideInVertically(tween(200)) { it },
        exit = slideOutVertically(tween(200)) { it }
    ) {
        BottomBar(navController = navController)
    }
}

@OptIn(ExperimentalSafeArgsApi::class)
@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination =
        navController.currentDestinationAsState().value ?: NavGraphs.root.startDestination
    val scope = rememberCoroutineScope()
    NavigationBar(
        Modifier
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        BottomBarDestination.entries.forEach { destination ->
            NavigationBarItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = currentDestination == destination.direction,
                onClick = {
                    if (currentDestination != destination.direction) {
                        scope.launch {
                            navController.navigate(
                                destination.route
                            )
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = stringResource(destination.label),
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = {
                    Text(text = stringResource(id = destination.label))
                }
            )
        }
    }
}