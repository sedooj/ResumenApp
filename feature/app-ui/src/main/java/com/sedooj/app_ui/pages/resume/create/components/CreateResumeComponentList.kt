package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sedooj.arch.viewmodel.auth.model.TabsModel
import com.sedooj.ui_kit.FilledButton

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