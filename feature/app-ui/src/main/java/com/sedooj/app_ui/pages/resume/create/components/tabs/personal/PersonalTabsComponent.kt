package com.sedooj.app_ui.pages.resume.create.components.tabs.personal

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sedooj.arch.viewmodel.auth.model.TabsModel

@Composable
fun SetupPersonalTabs(
    selectedTabId: Int,
    onSelectTab: (Int, TabsModel.PersonalTabs) -> Unit,
) {
    TabRow(selectedTabIndex = selectedTabId) {
        TabsModel.PersonalTabs.entries.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTabId == index,
                onClick = {
                    if (selectedTabId != index)
                        onSelectTab(index, tab)
                }, modifier = Modifier.clip(RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = stringResource(id = tab.title),
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    maxLines = 1
                )
            }
        }
    }
}