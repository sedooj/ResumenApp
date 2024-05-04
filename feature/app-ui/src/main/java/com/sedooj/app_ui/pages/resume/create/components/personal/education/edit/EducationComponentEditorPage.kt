package com.sedooj.app_ui.pages.resume.create.components.personal.education.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.arch.Routes
import com.sedooj.ui_kit.Screen

@Destination<RootGraph>(
    start = false,
    style = ScreensTransitions::class,
    route = Routes.EDUCATION_EDITOR,
)
@Composable
fun EducationComponentEditorPage(
    id: Int,
    educationStage: EducationStage,
    title: String,
    locationCity: String,
    enterDate: String,
    graduatedDate: String,
    faculty: String,
    speciality: String,
    resultNavigator: ResultBackNavigator<EditorEducation>,
) {

//    val title =
//        education.title.ifBlank { stringResource(id = com.sedooj.ui_kit.R.string.education) }
    Screen(
        modifier = Modifier.fillMaxSize(),
        title = title,
        alignment = Alignment.Top,
        navigationButton = {

        }
    ) {
        Text("editor", modifier = Modifier.clickable(
            onClick = {
                resultNavigator.navigateBack(
                    result = EditorEducation(
                        educationStage = EducationStage.NOT_SPECIFIED,
                        title = "blyaaa pizdec",
                        locationCity = "Oakston",
                        enterDate = "quidam",
                        graduatedDate = "labores",
                        faculty = "omnesque",
                        speciality = "posidonium"
                    )
                )
            }
        ))
    }
}

data class EditorEducation(
    var educationStage: EducationStage,
    var title: String,
    var locationCity: String,
    var enterDate: String,
    var graduatedDate: String,
    var faculty: String,
    var speciality: String,
): java.io.Serializable