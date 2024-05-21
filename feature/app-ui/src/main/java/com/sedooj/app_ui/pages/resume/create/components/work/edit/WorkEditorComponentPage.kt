package com.sedooj.app_ui.pages.resume.create.components.work.edit

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
import com.sedooj.app_ui.pages.resume.create.components.work.data.EditWork
import com.sedooj.app_ui.pages.resume.create.components.work.edit.data.WorkEditorPageContent
import com.sedooj.app_ui.pages.resume.create.components.work.edit.data.parseWorkEditorData
import com.sedooj.app_ui.pages.resume.create.components.work.edit.data.rememberWorkEditorDataMap
import com.sedooj.arch.Routes
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.FloatingSaveButton
import com.sedooj.ui_kit.components.LostDataAlert
import com.sedooj.ui_kit.screens.Screen

@Destination<RootGraph>(
    route = Routes.CREATE_RESUME_WORK_EDITOR,
    start = false,
    style = SlideScreenTransitions::class
)
@Composable
fun WorkEditorComponentPage(
    work: EditWork,
    resultNavigator: ResultBackNavigator<EditWork>,
) {
    val data = rememberWorkEditorDataMap(initInfo = work)
    var isDataSaved by remember { mutableStateOf(false) }
    var isDataEdited by remember { mutableStateOf(false) }
    var isLostDataAlertShow by remember { mutableStateOf(false) }
    BackHandler {}
    Screen(
        modifier = Modifier.fillMaxSize(),
        title = if (work.isEdit) work.company else stringResource(id = R.string.new_work),
        alignment = Alignment.Top,
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            val parsedData = parseWorkEditorData(data = data, initInfo = work)
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
        WorkEditorPageContent(
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