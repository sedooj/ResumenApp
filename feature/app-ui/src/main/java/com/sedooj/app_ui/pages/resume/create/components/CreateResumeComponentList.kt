package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.Routes
import com.sedooj.arch.viewmodel.auth.model.TabsModel
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.Screen

@Composable
fun CreateResumeComponentsPage(
    modifier: Modifier = Modifier,
    onSelect: () -> Unit,
) {
    TabsModel.Tabs.entries.forEach {
        FilledButton(
            label = stringResource(id = it.title), onClick = { onSelect() }, icon = painterResource(
                id = it.icon
            ), modifier = modifier
        )
    }

}