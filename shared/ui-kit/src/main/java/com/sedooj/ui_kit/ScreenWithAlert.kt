package com.sedooj.ui_kit

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithAlert(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationButton: @Composable () -> Unit = {},
    actionButton: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    paddingValues: Dp = 15.dp,
    alertDialog: @Composable () -> Unit,
    showAlert: Boolean,
    content: @Composable () -> Unit,
) {
    val blurRadius by animateDpAsState(
        targetValue = if (showAlert) 5.dp else 0.dp, label = ""
    )
    Scaffold(
        modifier = modifier.then(
            Modifier.blur(
                radius = blurRadius,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
        ),
        topBar = {
            if (title != null)
                CenterAlignedTopAppBar(title = {
                    Text(text = title, fontWeight = FontWeight.Bold)
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
                modifier = Modifier.fillMaxSize().padding(it),
                content = content,
                alignment = alignment,
                paddingValues = paddingValues,
                showAlert = showAlert,
                alertDialog = alertDialog
            )
        }
    )
}


@Composable
private fun ScaffoldContentComponent(
    modifier: Modifier,
    content: @Composable () -> Unit,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    paddingValues: Dp,
    alertDialog: @Composable () -> Unit,
    showAlert: Boolean,
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
                paddingValues,
                alignment = alignment
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
        if (showAlert)
            alertDialog()
    }
}