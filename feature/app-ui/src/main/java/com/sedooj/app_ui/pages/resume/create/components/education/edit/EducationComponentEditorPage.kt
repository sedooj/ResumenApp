package com.sedooj.app_ui.pages.resume.create.components.education.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.education.edit.data.EditorEducation
import com.sedooj.app_ui.pages.resume.create.components.education.edit.data.EducationEditorPageContent
import com.sedooj.app_ui.pages.resume.create.components.education.edit.data.parseEducationEditorData
import com.sedooj.app_ui.pages.resume.create.components.education.edit.data.rememberEditorEducationDataMap
import com.sedooj.arch.Routes
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.FloatingSaveButton
import com.sedooj.ui_kit.components.LostDataAlert
import com.sedooj.ui_kit.screens.Screen

@Destination<RootGraph>(
    start = false,
    style = SlideScreenTransitions::class,
    route = Routes.EDUCATION_EDITOR,
)
@Composable
fun EducationComponentEditorPage(
    education: EditorEducation,
    resultNavigator: ResultBackNavigator<EditorEducation>,
) {
    val data = rememberEditorEducationDataMap(initInfo = education)
    var isDataSaved by remember { mutableStateOf(false) }
    var isDataEdited by remember { mutableStateOf(false) }
    var isLostDataAlertShow by remember { mutableStateOf(false) }
    BackHandler {}
    Screen(
        modifier = Modifier.fillMaxSize(),
        title = if (education.isEdit) education.title else stringResource(id = R.string.new_education),
        alignment = Alignment.Top,
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            val parsedData = parseEducationEditorData(data = data, initInfo = education)
            FloatingSaveButton(
                onSave = {
                    resultNavigator.navigateBack(
                        result = parsedData
                    )
                    isDataSaved = true
                },
                isDataSaved = isDataSaved,
                isDataEdited = isDataEdited,
            )
        },
        hasBackButton = true,
        onBack = {
            if (isDataEdited)
                isLostDataAlertShow = true
            else
                resultNavigator.navigateBack()
        },
        alertDialog = {
            LostDataAlert(
                onDismiss = { isLostDataAlertShow = false },
                onConfirm = {
                    isLostDataAlertShow = false
                    resultNavigator.navigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        showAlert = isLostDataAlertShow
    ) {
        EducationEditorPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            onEdit = { key, value ->
                data[key] = value
                isDataEdited = true
                isDataSaved = false
            },
            data = data
        )
    }
}

