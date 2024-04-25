package com.sedooj.ui_kit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                alignment = alignment,
                paddingValues = paddingValues
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
    }
}