package com.sedooj.resumen.navigation.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sedooj.resumen.navigation.NavConfig

@Composable
fun SignInPage(navigateTo: (route: String) -> Unit) {
    Scaffold(
        topBar = {
            Text("${NavConfig.SIGN_IN}")
        },
        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {

            }
        }
    )
}