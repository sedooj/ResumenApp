package com.sedooj.app_ui.pages.resume.create.components.personal.second

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.app_ui.navigation.config.FadeScreensTransitions
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.EducationConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.SocialMediaConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.personal.second.content.SecondComponent
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.Screen

enum class SecondPageFields(
    @StringRes
    val fieldName: Int,
    val defaultValue: FieldValue,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    ABOUT_ME(
        fieldName = R.string.about_me,
        defaultValue = TextValue(""),
        readOnly = false
    ),
    PERSONAL_QUALITIES(
        fieldName = R.string.personal_qualities,
        defaultValue = TextValue(""),
        readOnly = false
    ),
    SOCIAL_MEDIA(
        fieldName = R.string.social_media,
        defaultValue = CustomValue(
            SocialMediaConvertibleContainer(
                listOf(
                    CreateResumeUseCase.PersonalInformation.SocialMedia(
                        type = "",
                        url = ""
                    )
                )
            )
        ),
        readOnly = true
    ),
    EDUCATION(
        fieldName = R.string.education,
        defaultValue = CustomValue(
            EducationConvertibleContainer(
                listOf(
                    CreateResumeUseCase.PersonalInformation.Education(
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

@Composable
private fun rememberDataMap(initInfo: CreateResumeUseCase.PersonalInformation?): SnapshotStateMap<SecondPageFields, FieldValue> {
    return remember {
        mutableStateMapOf(
            SecondPageFields.SOCIAL_MEDIA to
                    if (initInfo?.socialMedia == null)
                        CustomValue(
                            SocialMediaConvertibleContainer(
                                listOf(
                                    CreateResumeUseCase.PersonalInformation.SocialMedia(
                                        type = "",
                                        url = ""
                                    )
                                )
                            )
                        )
                    else
                        CustomValue(
                            SocialMediaConvertibleContainer(
                                initInfo.socialMedia.map {
                                    CreateResumeUseCase.PersonalInformation.SocialMedia(
                                        type = it.type,
                                        url = it.url
                                    )
                                }
                            )
                        ),

            SecondPageFields.ABOUT_ME to if (initInfo?.aboutMe != null) TextValue(initInfo.aboutMe!!) else TextValue(
                ""
            ),
            SecondPageFields.PERSONAL_QUALITIES to if (initInfo?.personalQualities != null) TextValue(
                initInfo.personalQualities
            ) else TextValue(""),
            SecondPageFields.EDUCATION to if (initInfo?.education == null) CustomValue(
                EducationConvertibleContainer(
                    listOf(
                        CreateResumeUseCase.PersonalInformation.Education(
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
            ) else CustomValue(
                EducationConvertibleContainer(
                    initInfo.education.map {
                        CreateResumeUseCase.PersonalInformation.Education(
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

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_PERSONAL_SECONDARY,
    style = FadeScreensTransitions::class
)
@Composable
fun SecondPersonalComponentPage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    BackHandler {
//        if (!isChangesSaved)
        isAlertDialogVisible = true
//        else
//            destinationsNavigator.navigateUp()
    }
    val personal = createResumeViewModel.uiState.collectAsState().value.personalInformation
    val data = rememberDataMap(personal)

    Screen(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        title = stringResource(id = R.string.personal_information),
        navigationButton = {
            IconButton(onClick = {
//                if (!isChangesSaved)
                isAlertDialogVisible = true
//                else
//                    destinationsNavigator.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(
                        id = R.string.go_back
                    ),
                    Modifier.size(15.dp)
                )
            }
        },
        alignment = Alignment.Top,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
//            AnimatedVisibility(
//                visible = !isChangesSaved,
//                enter = scaleIn(tween(200)),
//                exit = scaleOut(tween(200))
//            ) {
//                FloatingActionButton(onClick = {
//
//                        isChangesSaved = true
//
//                }) {
//                    Icon(
//                        imageVector = Icons.Filled.Done,
//                        contentDescription = stringResource(id = R.string.save)
//                    )
//                }
//            }
        }
    ) {
        SecondComponent(
            data = data,
            onValueChange = { field, value ->
                data[field] = value
            },
            modifier = Modifier.fillMaxSize(),
        )
        //TODO()
    }
}