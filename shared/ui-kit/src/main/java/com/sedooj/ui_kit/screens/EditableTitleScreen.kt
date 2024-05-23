package com.sedooj.ui_kit.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.textField.TitleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableTitleScreen(
    modifier: Modifier = Modifier,
    title: TextFieldValue,
    navigationButton: @Composable () -> Unit = {},
    actionButton: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    paddingValues: Dp = 15.dp,
    onValueChange: (TextFieldValue) -> Unit,
    alertDialog: (@Composable () -> Unit)? = null,
    showAlert: Boolean = false,
    onBack: (() -> Unit)? = null,
    hasBackButton: Boolean = false,
    hasError: Boolean = false,
    bottomBar: (@Composable () -> Unit)? = null,
    errorContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit,
) {
    val blurRadius by animateDpAsState(
        targetValue = if (showAlert) 5.dp else 0.dp, label = ""
    )
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = modifier.then(
            Modifier.blur(
                radius = blurRadius,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
        ),
        topBar = {
            if (!hasError)
                TopAppBar(
                    title = {
                        TitleTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = title,
                            onValueChange = {
                                onValueChange(it)
                            },
                            maxLines = 1,
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            })
                        )
                    }, navigationIcon = {
                        if (!hasBackButton)
                            navigationButton()
                        else
                            BackNavButton(
                                modifier = Modifier.size(15.dp),
                                onBack = onBack!!
                            )
                    }, actions = {
                        actionButton()
                    })
        },
        bottomBar = {
            if (bottomBar != null) {
                bottomBar()
            }
        },
        floatingActionButton = {
            if (!hasError)
                floatingActionButton()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        content = {
            ScaffoldContentComponent(
                modifier = modifier.padding(it),
                content = if (hasError) errorContent else content,
                alignment = if (hasError) Alignment.CenterVertically else alignment,
                paddingValues = paddingValues,
                showAlert = showAlert,
                alertDialog = alertDialog
            )
        }
    )
}

@Composable
private fun BackNavButton(
    onBack: (() -> Unit),
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onBack) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = stringResource(
                id = R.string.go_back
            ),
            modifier = modifier
        )
    }
}

@Composable
private fun ScaffoldContentComponent(
    modifier: Modifier,
    content: @Composable () -> Unit,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    paddingValues: Dp,
    alertDialog: (@Composable () -> Unit)? = null,
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
        if (showAlert && alertDialog != null)
            alertDialog()
    }
}