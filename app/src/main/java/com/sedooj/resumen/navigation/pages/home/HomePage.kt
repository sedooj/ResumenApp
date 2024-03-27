package com.sedooj.resumen.navigation.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.resumen.R
import com.sedooj.resumen.navigation.config.ScreensTransitions
import com.sedooj.resumen.navigation.pages.Routes
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation
import kotlin.random.Random

@Destination<RootGraph>(start = false, route = Routes.HOME, style = ScreensTransitions::class)
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator
) {
    val token = rememberSaveable { Random.nextInt() }
    KitPageWithNavigation(
        title = stringResource(id = R.string.app_name) + "$token",
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        KitFilledButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.create_resume),
            onClick = {

            })
    }
}


