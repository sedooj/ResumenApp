package com.sedooj.resumen.navigation.pages.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Destination<RootGraph>(
    start = true,
    route = Routes.SIGN_IN,
    style = ScreensTransitions::class
)
@Composable
fun SignInPage(
    destinationsNavigator: DestinationsNavigator
) {
    KitPageWithNavigation(
        title = stringResource(id = R.string.app_name), modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        KitFilledButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.sign_in),
            onClick = {
                destinationsNavigator.popBackStack()
                destinationsNavigator.navigate(Routes.HOME)
            })
        Text(text = stringResource(id = R.string.no_account), modifier = Modifier.clickable {
            destinationsNavigator.navigate(Routes.SIGN_UP)
        })
    }
}