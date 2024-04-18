package com.sedooj.ui_kit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationButton: @Composable () -> Unit = {},
    actionButton: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (title != null)
                TopAppBar(title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = title, fontWeight = FontWeight.Bold)
                    }

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
        content = {
            ScaffoldContentComponent(
                modifier = modifier.padding(it),
                content = content,
                alignment = alignment
            )
        }
    )
}


@Composable
private fun ScaffoldContentComponent(
    modifier: Modifier,
    content: @Composable () -> Unit,
    alignment: Alignment.Vertical = Alignment.CenterVertically
) {
    Surface(
        modifier = modifier
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = alignment
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }
}