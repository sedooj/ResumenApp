package com.sedooj.app_ui.pages.resume.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.resume.create.components.CreateResumeComponentsPage
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.EditableTitleScreen
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.R.drawable
import com.sedooj.ui_kit.R.string

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME,
    style = ScreensTransitions::class
)
@Composable
fun CreateResumePage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    val viewModel = createResumeViewModel.uiState.collectAsState().value
    val defaultTitle = "New resume"
    val title = remember {
        mutableStateOf(
            TextFieldValue(
                text = viewModel.title,
                selection = if (viewModel.title == defaultTitle) TextRange(
                    0,
                    defaultTitle.length
                ) else TextRange(0)
            )
        )
    }
    val focusManager = LocalFocusManager.current
    BackHandler {
        isAlertDialogVisible = true
    }
    EditableTitleScreen(
        title = title.value,
        modifier = Modifier
            .fillMaxSize(),
        alignment = Alignment.Top,
        paddingValues = 0.dp,
        onValueChange = {
            title.value = TextFieldValue(
                text = it.text,
                selection = TextRange(it.text.length)
            )
            createResumeViewModel.updateTitle(it.text)
        },
        navigationButton = {
            IconButton(onClick = {
                isAlertDialogVisible = true
            }) {
                Icon(
                    painter = painterResource(id = drawable.arrow_back),
                    contentDescription = stringResource(
                        id = string.go_back
                    ),
                    Modifier.size(15.dp)
                )
            }
        },
        actionButton = {
            IconButton(onClick = { focusManager.moveFocus(FocusDirection.Left) }) {
                Icon(
                    painter = painterResource(id = drawable.edit_pen),
                    contentDescription = stringResource(
                        id = string.edit_resume
                    ),
                    Modifier.size(20.dp)
                )
            }
        }
    ) {
        SetupComponentList(
            onSelect = {
                destinationsNavigator.navigate(it)
            }
        )
    }
    ShowAlert(
        setVisible = { isAlertDialogVisible = it },
        visible = isAlertDialogVisible,
        onConfirm = {
            destinationsNavigator.navigateUp()
            createResumeViewModel.dropUiState()
        })
}

@Composable
private fun ShowAlert(setVisible: (Boolean) -> Unit, visible: Boolean, onConfirm: () -> Unit) {
    if (visible)
        AlertDialog(
            onDismissRequest = { setVisible(false) },
            confirmButton = {
                FilledButton(
                    label = stringResource(id = string.cancel),
                    onClick = {
                        setVisible(false)
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp)
                )
            },
            dismissButton = {
                FilledButton(
                    label = stringResource(id = string.yes),
                    onClick = {
                        setVisible(false)
                        onConfirm()
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        disabledContentColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ),
                )
            },
            text = {
                Text(
                    text = stringResource(id = string.changes_is_not_saved),
                    color = MaterialTheme.colorScheme.surfaceTint,
                    overflow = TextOverflow.Ellipsis,
                )
            })
}

@Composable
private fun SetupComponentList(
    onSelect: (String) -> Unit,
) {
    CreateResumeComponentsPage(
        modifier = Modifier.fillMaxWidth(),
        onSelect = {
            onSelect(it)
        }
    )
}