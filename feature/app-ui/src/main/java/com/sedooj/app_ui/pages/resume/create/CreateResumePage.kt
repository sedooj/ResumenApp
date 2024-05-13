package com.sedooj.app_ui.pages.resume.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.CreateResumeComponentsPage
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R.drawable
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.components.LostDataAlert
import com.sedooj.ui_kit.screens.EditableTitleScreen

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME,
    style = SlideScreenTransitions::class
)
@Composable
fun CreateResumePage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    var isDataSaved by remember { mutableStateOf(false) }
    var isDataEdited by remember { mutableStateOf(false) }
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
        hasBackButton = true,
        onBack = {
            isAlertDialogVisible = true
        },
        alertDialog = {
            LostDataAlert(onDismiss = { isAlertDialogVisible = false }, onConfirm = {
                destinationsNavigator.navigateUp()
                createResumeViewModel.dropUiState()
            })
        },
        showAlert = isAlertDialogVisible,
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

}

@Composable
private fun SetupComponentList(
    onSelect: (Direction) -> Unit,
) {
    CreateResumeComponentsPage(
        modifier = Modifier.fillMaxWidth(),
        onSelect = {
            onSelect(it)
        }
    )
}