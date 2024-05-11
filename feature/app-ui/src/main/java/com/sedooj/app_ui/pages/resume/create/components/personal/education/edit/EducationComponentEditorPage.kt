package com.sedooj.app_ui.pages.resume.create.components.personal.education.edit

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.DateValue
import com.sedooj.app_ui.pages.resume.create.components.generic.EducationStageConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.arch.Routes
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.screens.Screen
import com.sedooj.ui_kit.screens.ScreenWithAlert
import java.io.Serializable

enum class EducationComponentEditorPageFields(
    @StringRes
    val fieldName: Int,
    val defaultValue: FieldValue,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    TITLE(
        fieldName = R.string.education_editor_title,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    LocationCity(
        fieldName = R.string.city,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    FACULTY(
        fieldName = R.string.faculty,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    SPECIALITY(
        fieldName = R.string.speciality,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    EDUCATION_STAGE(
        fieldName = R.string.education_stage,
        defaultValue = CustomValue(EducationStageConvertibleContainer(EducationStage.NOT_SPECIFIED)),
        readOnly = true,
        suggestions = listOf(
            CustomValue(EducationStageConvertibleContainer(EducationStage.NOT_SPECIFIED)),
            CustomValue(EducationStageConvertibleContainer(EducationStage.COLLEGE)),
            CustomValue(EducationStageConvertibleContainer(EducationStage.INSTITUTE)),
            CustomValue(EducationStageConvertibleContainer(EducationStage.UNIVERSITY)),
        )
    ),
    ENTER_DATE(
        fieldName = R.string.education_enter_date,
        defaultValue = DateValue(date = ""),
        readOnly = true
    ),
    GRADUATE_DATE(
        fieldName = R.string.education_graduation_date,
        defaultValue = DateValue(date = ""),
        readOnly = true
    ),
}

@Composable
private fun rememberEducationDataMap(initInfo: EditorEducation): SnapshotStateMap<EducationComponentEditorPageFields, FieldValue> {
    return remember {
        mutableStateMapOf(
            EducationComponentEditorPageFields.TITLE to if (initInfo.title.isEmpty()) TextValue("") else TextValue(
                initInfo.title
            ),
            EducationComponentEditorPageFields.LocationCity to if (initInfo.locationCity.isEmpty()) TextValue(
                ""
            ) else TextValue(initInfo.locationCity),
            EducationComponentEditorPageFields.FACULTY to if (initInfo.faculty.isBlank()) TextValue(
                ""
            ) else TextValue(initInfo.faculty),
            EducationComponentEditorPageFields.SPECIALITY to if (initInfo.speciality.isBlank()) TextValue(
                ""
            ) else TextValue(initInfo.speciality),
            EducationComponentEditorPageFields.EDUCATION_STAGE to
                    if (initInfo.educationStage == EducationStage.NOT_SPECIFIED)
                        CustomValue(
                            EducationStageConvertibleContainer(
                                EducationStage.NOT_SPECIFIED
                            )
                        ) else
                        CustomValue(
                            EducationStageConvertibleContainer(
                                initInfo.educationStage
                            )
                        ),
            EducationComponentEditorPageFields.ENTER_DATE to if (initInfo.enterDate.isBlank()) DateValue(
                ""
            ) else DateValue(initInfo.enterDate),
            EducationComponentEditorPageFields.GRADUATE_DATE to if (initInfo.graduatedDate.isBlank()) DateValue(
                ""
            ) else DateValue(initInfo.graduatedDate)
        )
    }
}

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
    val data = rememberEducationDataMap(initInfo = education)
    var isDataEdited by remember { mutableStateOf(false) }
    var isLostDataAlertShow by remember { mutableStateOf(false) }
    BackHandler {
        if (isDataEdited)
            isLostDataAlertShow = true
        else
            resultNavigator.navigateBack()
    }
    Screen(
        modifier = Modifier.fillMaxSize(),
        title = if (education.isEdit) education.title else stringResource(id = R.string.new_education),
        alignment = Alignment.Top,
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            val editedData = parseData(initInfo = education, data = data)
            FloatingActionButton(onClick = {
                resultNavigator.navigateBack(
                    result = editedData
                )
            }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.done)
                )
            }
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
        EducationComponentEditorPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            onEdit = { key, value ->
                data[key] = value
                isDataEdited = true
            },
            data = data
        )
    }
}

@Composable
private fun LostDataAlert(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.warning),
                contentDescription = stringResource(
                    id = R.string.warning
                ),
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.changes_is_not_saved),
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        dismissButton = {
            FilledButton(
                label = stringResource(id = R.string.quit),
                onClick = { onConfirm() },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            )
        },
        confirmButton = {
            FilledButton(
                label = stringResource(id = R.string.continue_edit),
                onClick = { onDismiss() },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            )
        },
        modifier = modifier
    )
}

@Composable
private fun parseData(
    data: Map<EducationComponentEditorPageFields, FieldValue>,
    initInfo: EditorEducation,
): EditorEducation {
    val title =
        data[EducationComponentEditorPageFields.TITLE]?.asStringValue() ?: initInfo.title
    val locationCity =
        data[EducationComponentEditorPageFields.LocationCity]?.asStringValue()
            ?: initInfo.locationCity
    val enterDate = data[EducationComponentEditorPageFields.ENTER_DATE]?.asStringValue()
        ?: initInfo.enterDate
    val graduatedDate =
        data[EducationComponentEditorPageFields.GRADUATE_DATE]?.asStringValue()
            ?: initInfo.graduatedDate
    val faculty =
        data[EducationComponentEditorPageFields.FACULTY]?.asStringValue() ?: initInfo.faculty
    val speciality = data[EducationComponentEditorPageFields.SPECIALITY]?.asStringValue()
        ?: initInfo.speciality
    val educationStage =
        (data[EducationComponentEditorPageFields.EDUCATION_STAGE]?.asInitialValue() as EducationStageConvertibleContainer?)?.value
            ?: initInfo.educationStage
    return EditorEducation(
        educationStage = educationStage,
        title = title,
        locationCity = locationCity,
        enterDate = enterDate,
        graduatedDate = graduatedDate,
        faculty = faculty,
        speciality = speciality,
        id = initInfo.id
    )
}

data class EditorEducation(
    var id: Int,
    var educationStage: EducationStage,
    var title: String,
    var locationCity: String,
    var enterDate: String,
    var graduatedDate: String,
    var faculty: String,
    var speciality: String,
    var isEdit: Boolean = false,
) : Serializable