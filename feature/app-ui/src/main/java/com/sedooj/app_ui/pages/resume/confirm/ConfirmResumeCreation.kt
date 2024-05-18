package com.sedooj.app_ui.pages.resume.confirm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.template.TemplateEntity
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.confirm.data.ConfirmResumeCreationContent
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
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
                    label = stringResource(id = string.save_new_resume),
                    onClick = { /*TODO*/ },
                    enabled =
                    selectedTemplateIndexValue != -1 && selectedTemplateEntityValue != null,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                )
            }
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