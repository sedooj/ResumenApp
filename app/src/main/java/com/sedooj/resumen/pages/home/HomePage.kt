package com.sedooj.resumen.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.resumen.R
import com.sedooj.resumen.navigation.config.ScreensTransitions
import com.sedooj.resumen.pages.Routes
import com.sedooj.resumen.storage.db.AppDatabase
import com.sedooj.resumen.ui.kit.KitFilledButton
import com.sedooj.resumen.ui.kit.KitPageWithNavigation
import kotlinx.coroutines.launch
import kotlin.random.Random

@Destination<RootGraph>(start = false, route = Routes.HOME, style = ScreensTransitions::class)
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
) {
    val token = rememberSaveable { Random.nextInt() }
    val context = LocalContext.current
    val db =
        Room.databaseBuilder(
            context = context,
            AppDatabase::class.java, "resumen-app-db"
        ).build()

    val id = rememberSaveable { mutableIntStateOf(-1) }
    val username = rememberSaveable { mutableStateOf("")}
    val password = rememberSaveable { mutableStateOf("")}
    LaunchedEffect(key1 = id.intValue == -1) {
        val authorizationData = db.authUserDao().getAuthorizationData()
        if (authorizationData != null) {
            id.intValue = authorizationData.id
            username.value = authorizationData.username
            password.value = authorizationData.password
        }
    }
    KitPageWithNavigation(
        title = stringResource(id = R.string.app_name) + "$token",
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "${id.intValue}")
        Text(text = username.value)
        Text(text = password.value)
        KitFilledButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.create_resume),
            onClick = {

            })
    }
}


