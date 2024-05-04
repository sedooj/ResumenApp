package com.sedooj.app_ui.pages.resume.create.components.personal.education

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.EducationConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.Screen

enum class EducationComponentPageFields(
    @StringRes
    val fieldName: Int,
    val defaultValue: FieldValue,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    EDUCATION(
        fieldName = R.string.education,
        defaultValue = CustomValue(
            EducationConvertibleContainer(
                listOf(
                    Education(
                        educationStage = EducationStage.NOT_SPECIFIED,
                        title = "",
                        locationCity = "",
                        enterDate = "",
                        graduatedDate = "",
                        faculty = "",
                        speciality = ""
                    )
                )
            )
        ),
        readOnly = false
    )
}


@Destination<RootGraph>(start = false, style = ScreensTransitions::class, route = Routes.EDUCATION)
@Composable
fun EducationComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    Screen(
        title = stringResource(id = R.string.education),
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Top
    ) {
        EduList(createResumeViewModel = createResumeViewModel, onEdit = { i, edu ->

        })
    }
}

@Composable
fun EduList(
    createResumeViewModel: CreateResumeViewModel,
    onEdit: (Int, Education) -> Unit,
    modifier: Modifier = Modifier,
) {
    val education =
        createResumeViewModel.uiState.collectAsState().value.personalInformation?.education
            ?: emptyList()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (education.isEmpty())
            Box(modifier = modifier, contentAlignment = Alignment.Center, content = {
                Text(
                    text = stringResource(id = R.string.put_information_about_education),
                    textAlign = TextAlign.Center
                )
            })
        else {
            education.forEachIndexed { i, edu ->
                ListItem(
                    headlineContent = {
                        Text(text = edu.title)
                    }, supportingContent = {

                    }, leadingContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.education),
                            contentDescription = edu.title,
                            modifier = Modifier.size(25.dp)
                        )
                    }, modifier = modifier.clickable(onClick = {
                        onEdit(
                            i,
                            Education(
                                educationStage = edu.educationStage,
                                title = edu.title,
                                locationCity = edu.locationCity,
                                enterDate = edu.enterDate,
                                graduatedDate = edu.graduatedDate,
                                faculty = edu.faculty,
                                speciality = edu.speciality
                            )
                        )
                    })
                )
            }
        }

    }
}