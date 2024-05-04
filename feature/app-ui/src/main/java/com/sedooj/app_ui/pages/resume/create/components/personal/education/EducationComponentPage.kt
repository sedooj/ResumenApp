package com.sedooj.app_ui.pages.resume.create.components.personal.education

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
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
                emptyList()
            )
        ),
        readOnly = false
    )
}

@Composable
private fun rememberDataMap(initInfo: List<Education>?): SnapshotStateMap<EducationComponentPageFields, CustomValue<EducationConvertibleContainer>> {
    return remember {
        mutableStateMapOf(
            EducationComponentPageFields.EDUCATION to if (initInfo == null) CustomValue(
                EducationConvertibleContainer(
                    emptyList()
                )
            ) else CustomValue(
                EducationConvertibleContainer(
                    initInfo.map {
                        Education(
                            educationStage = it.educationStage,
                            title = it.title,
                            locationCity = it.locationCity,
                            enterDate = it.enterDate,
                            graduatedDate = it.graduatedDate,
                            faculty = it.faculty,
                            speciality = it.speciality
                        )
                    }
                )
            )
        )
    }
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
        alignment = Alignment.Top,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                createResumeViewModel
            }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "add edu")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        EduList(createResumeViewModel = createResumeViewModel, onEdit = { i, edu ->

        })
    }
}

@Composable
fun EduList(
    createResumeViewModel: CreateResumeViewModel,
    onEdit: (EducationComponentPageFields, Education) -> Unit,
    modifier: Modifier = Modifier,
) {
    val education =
        createResumeViewModel.uiState.collectAsState().value.personalInformation?.education
            ?: emptyList()
    if (education.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center, content = {
            Text(
                text = stringResource(id = R.string.put_information_about_education),
                textAlign = TextAlign.Center
            )
        })
    } else {
        val data = rememberDataMap(initInfo = education)
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            data.toSortedMap().forEach { (key, value) ->
                val eduAsValue = value.value.asEducationList()
                eduAsValue.forEach {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = it.title,
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }, supportingContent = {

                        }, leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.education),
                                contentDescription = it.title,
                                modifier = Modifier.size(40.dp)
                            )
                        }, modifier = modifier.clickable(onClick = {
                            onEdit(
                                key, it
                            )
                        })
                    )
                }

            }
        }
    }
}