package com.sedooj.resumen.ui.kit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sedooj.resumen.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitPageWithNavigation(
    title: String,
    navigationButton: @Composable () -> Unit = {},
    actionButton: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(text = title)
            }, navigationIcon = {
                navigationButton()
            }, actions = {
                actionButton()
            })
        },
        floatingActionButton = {
            floatingActionButton()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        bottomBar = {
            NavigationBarComponent()
        },
        content = {
            ScaffoldContentComponent(modifier = modifier.padding(it)) {
                KitFilledButton(label = "Login", onClick = { /*TODO*/ })
            }
        })
}

@Composable
private fun NavigationBarComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val selectedPage = remember { mutableStateOf(Screens.Home.MAIN.route) }
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
                selected = selectedPage.value == screen.route,
                onClick = {
                    if (selectedPage.value != screen.route)
                        selectedPage.value = screen.route
                },
                icon = {
                    screen.icon()
                }
            )
        }
    }
}

@Composable
private fun ScaffoldContentComponent(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }
}