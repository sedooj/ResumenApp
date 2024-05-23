package com.sedooj.app_ui.pages.resume.confirm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.template.TemplateEntity
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.confirm.data.ConfirmResumeCreationContent
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeUiState
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.screens.Screen

@Destination<RootGraph>(
    route = Routes.CREATE_RESUME_CONFIRMATION,
    style = SlideScreenTransitions::class
)
@Composable
fun ConfirmResumeCreationPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    val resume = createResumeViewModel.uiState.collectAsState().value
    var selectedTemplateIndexValue by remember { mutableIntStateOf(-1) }
    var selectedTemplateEntityValue by remember { mutableStateOf<TemplateEntity?>(null) }
    val scope = rememberCoroutineScope()
    var hasError by remember {
        mutableStateOf(false)
    }
    var isAlertDialogVisible by remember {
        mutableStateOf(false)
    }
    Screen(
        title = resume.title,
        hasBackButton = true,
        onBack = {
            navigator.navigateUp()
        },
        alignment = Alignment.Top,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                FilledButton(
                    label = if (resume.resumeId == null) stringResource(id = string.save_new_resume) else stringResource(
                        id = string.edit_resume
                    ),
                    onClick = {
                        parseData(
                            createResumeViewModel = createResumeViewModel,
                            resume = resume,
                            onError = {
                                hasError = true
                                isAlertDialogVisible = true
                            },
                            onSuccess = {
                                createResumeViewModel.dropUiState()
                                navigator.popBackStack()
                                navigator.navigate(HomeDestination)
                            })
                    },
                    enabled =
//                    selectedTemplateIndexValue != -1 && selectedTemplateEntityValue != null,
                    !hasError,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                )
            }
        },
        showAlert = isAlertDialogVisible,
        alertDialog = {
            BadInputAlert(
                onDismiss = {
                    isAlertDialogVisible = false
                },
                onConfirm = {
                    isAlertDialogVisible = false
                    navigator.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        ConfirmResumeCreationContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            onSelect = { index, templateEntity ->
                selectedTemplateIndexValue = index
                selectedTemplateEntityValue = templateEntity
            },
            selectedTemplateIndexValue = selectedTemplateIndexValue,
            selectedTemplateEntity = selectedTemplateEntityValue
        )
    }
}

private fun parseData(
    resume: CreateResumeUiState,
    onSuccess: () -> Unit,
    onError: () -> Unit,
    createResumeViewModel: CreateResumeViewModel,
) {
    var success = false
    if (resume.resumeId != null) {
        if (createResumeViewModel.save())
            success = true
    } else {
        if (createResumeViewModel.push()) {
            success = true
        }
    }
    if (success) {
        onSuccess()
    } else {
        onError()
    }
}

@Composable
private fun BadInputAlert(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.warning),
                contentDescription = stringResource(
                    id = string.warning
                ),
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = stringResource(id = string.error_occurred_while_save_resume),
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            FilledButton(
                label = stringResource(id = string.back),
                onClick = onConfirm,
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            )
        },
        modifier = modifier
    )
}