package com.sedooj.app_ui.pages.resume.create.components.education.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.ramcosta.composedestinations.generated.destinations.EducationEditorDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.pages.resume.create.components.education.edit.data.EducationComponentData
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.FloatingAddButton

class EducationListComponent {

    @Composable
    fun Content(
        educationList: List<CreateResumeUseCase.PersonalInformation.Education>?,
        onEdit: (Int, CreateResumeUseCase.PersonalInformation.Education) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        EducationListComponentContent().GetContent(
            modifier = modifier, educationList = educationList, onEdit = onEdit
        )
    }

    fun createOrEdit(
        navigator: DestinationsNavigator,
        id: Int,
        education: CreateResumeUseCase.PersonalInformation.Education? = null,
    ) {
        EducationListComponentData().createOrEdit(
            navigator = navigator,
            id = id,
            education = education
        )
    }

    @Composable
    fun FloatingActionButton(
        navigator: DestinationsNavigator,
        educationList: List<CreateResumeUseCase.PersonalInformation.Education>?,
    ) {
        FloatingAddButton(onClick = dropUnlessResumed {
            EducationListComponent().createOrEdit(
                navigator = navigator,
                id = educationList?.lastIndex?.plus(1) ?: 0
            )
        })
    }

}

private class EducationListComponentData {
    fun createOrEdit(
        navigator: DestinationsNavigator,
        id: Int,
        education: CreateResumeUseCase.PersonalInformation.Education? = null,
    ) {
        if (education == null)
            navigator.navigate(
                EducationEditorDestination(
                    EducationComponentData.EditorEducation(
                        id = id,
                        educationStage = EducationStage.NOT_SPECIFIED,
                        title = "",
                        locationCity = "",
                        enterDate = "",
                        graduatedDate = "",
                        faculty = "",
                        speciality = ""
                    )
                )
            ) {
                launchSingleTop = true
            }
        else
            navigator.navigate(
                EducationEditorDestination(
                    EducationComponentData.EditorEducation(
                        id = id,
                        educationStage = education.educationStage,
                        title = education.title,
                        locationCity = education.locationCity,
                        enterDate = education.enterDate,
                        graduatedDate = education.graduatedDate,
                        faculty = education.faculty,
                        speciality = education.speciality,
                        isEdit = true
                    )
                )
            ) {
                launchSingleTop = true
            }
    }
}

private class EducationListComponentContent {
    @Composable
    fun GetContent(
        educationList: List<CreateResumeUseCase.PersonalInformation.Education>?,
        onEdit: (Int, CreateResumeUseCase.PersonalInformation.Education) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        if (educationList.isNullOrEmpty())
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
                Text(
                    text = stringResource(id = R.string.put_information_about_education),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
            })
        else
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                educationList.forEachIndexed { index, education ->
                    ListItem(
                        headlineContent = {
                            Text(
                                text = education.title,
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }, supportingContent = {

                        }, leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.education),
                                contentDescription = education.title,
                                modifier = Modifier.size(40.dp)
                            )
                        }, modifier = modifier.clickable(onClick = dropUnlessResumed {
                            onEdit(
                                index, education
                            )
                        })
                    )
                }
            }
    }

}