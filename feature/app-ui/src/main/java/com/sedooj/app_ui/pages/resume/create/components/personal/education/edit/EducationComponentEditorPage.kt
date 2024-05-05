package com.sedooj.app_ui.pages.resume.create.components.personal.education.edit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.EducationStageConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.arch.Routes
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.Screen
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
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
    GRADUATE_DATE(
        fieldName = R.string.education_graduation_date,
        defaultValue = TextValue(text = ""),
        readOnly = false
    ),
}

@Composable
private fun rememberEducationDataMap(initInfo: EditorEducation): SnapshotStateMap<EducationComponentEditorPageFields, FieldValue> {
    return remember {
        mutableStateMapOf(
            EducationComponentEditorPageFields.TITLE to if (initInfo.title.isEmpty()) TextValue("") else TextValue(initInfo.title),
            EducationComponentEditorPageFields.LocationCity to if (initInfo.locationCity.isEmpty()) TextValue("") else TextValue(initInfo.locationCity),
            EducationComponentEditorPageFields.FACULTY to if (initInfo.faculty.isBlank()) TextValue("") else TextValue(initInfo.faculty),
            EducationComponentEditorPageFields.SPECIALITY to if (initInfo.speciality.isBlank()) TextValue("") else TextValue(initInfo.speciality),
            EducationComponentEditorPageFields.EDUCATION_STAGE to if (initInfo.educationStage == EducationStage.NOT_SPECIFIED) CustomValue(
                EducationStageConvertibleContainer(EducationStage.NOT_SPECIFIED)
            ) else CustomValue(EducationStageConvertibleContainer(initInfo.educationStage)),
            EducationComponentEditorPageFields.ENTER_DATE to if (initInfo.enterDate.isBlank()) TextValue("") else TextValue(initInfo.enterDate),
            EducationComponentEditorPageFields.GRADUATE_DATE to if (initInfo.graduatedDate.isBlank()) TextValue("") else TextValue(initInfo.graduatedDate)
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
    Screen(
        modifier = Modifier.fillMaxSize(),
        title = education.title,
        alignment = Alignment.Top,
        navigationButton = {
            val title = data[EducationComponentEditorPageFields.TITLE]?.asStringValue() ?: "Empty field"
            val locationCity = data[EducationComponentEditorPageFields.LocationCity]?.asStringValue() ?: "Empty field"
            val enterDate = data[EducationComponentEditorPageFields.ENTER_DATE]?.asStringValue() ?: "Empty field"
            val graduatedDate = data[EducationComponentEditorPageFields.GRADUATE_DATE]?.asStringValue() ?: "Empty field"
            val faculty = data[EducationComponentEditorPageFields.FACULTY]?.asStringValue() ?: "Empty field"
            val speciality = data[EducationComponentEditorPageFields.FACULTY]?.asStringValue() ?: "Empty field"
            IconButton(onClick = {
                resultNavigator.navigateBack(
                    result = EditorEducation(
                        educationStage = EducationStage.NOT_SPECIFIED,
                        title = title,
                        locationCity = locationCity,
                        enterDate = enterDate,
                        graduatedDate = graduatedDate,
                        faculty = faculty,
                        speciality = speciality,
                        id = education.id
                    )
                )
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(
                        id = R.string.go_back
                    ),
                    Modifier.size(15.dp)
                )
            }
        }
    ) {
        EducationComponentEditorPageContent(
            modifier = Modifier.fillMaxSize(),
            onEdit = { key, value ->
                data[key] = value
            },
            data = data
        )
    }
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
) : Serializable