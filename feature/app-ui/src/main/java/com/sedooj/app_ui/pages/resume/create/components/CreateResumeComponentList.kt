package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun CreateResumeComponentsPage(
    modifier: Modifier = Modifier,
    onSelect: (Direction) -> Unit,
) {
    ComponentList.entries.forEach {
        ListItem(
            headlineContent = {
                Text(text = stringResource(id = it.title))
            }, supportingContent = {
                if (it.subTitle != null)
                    Text(text = stringResource(id = it.subTitle))
            }, leadingContent = {
                Icon(
                    painter = painterResource(id = it.icon),
                    contentDescription = stringResource(id = it.title),
                    modifier = Modifier.size(25.dp)
                )
            }, modifier = modifier.clickable(onClick = {
                onSelect(it.direction)
            })
        )
    }

}