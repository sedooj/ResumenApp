package com.sedooj.resumen.navigation.pages

import androidx.compose.runtime.Composable
import com.sedooj.resumen.ui.kit.KitPageWithNavigation

@Composable
fun SignInPage(navigateTo: (route: String) -> Unit) {
    KitPageWithNavigation(title = "Resumen")
}