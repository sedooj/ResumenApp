package com.sedooj.resumen.navigation.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sedooj.resumen.R
import com.sedooj.resumen.navigation.Screens
import com.sedooj.resumen.navigation.auth
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation

@Composable
fun SignInPage(
    login:() -> Unit,
    navigateTo: (route: String) -> Unit
) {
    KitPageWithNavigation(title = stringResource(id = R.string.app_name)) {
        KitFilledButton(label = "Login", onClick = { login() })
    }
}