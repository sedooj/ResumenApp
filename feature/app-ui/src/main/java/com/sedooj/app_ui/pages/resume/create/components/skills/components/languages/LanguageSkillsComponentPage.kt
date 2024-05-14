package com.sedooj.app_ui.pages.resume.create.components.skills.components.languages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.skills.components.languages.data.LanguageSkillsComponent
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.screens.Screen

@Destination<RootGraph>(
    route = Routes.CREATE_RESUME_SKILLS_LANGUAGE,
    style = SlideScreenTransitions::class
)
@Composable
fun LanguageSkillsComponentPage(
    navigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
//    resultRecipient: ResultRecipient<, EducationComponentData.EditorEducation>,
) {
    BackHandler {}
    val languageSkillList =
        createResumeViewModel.uiState.collectAsState().value.skillsInformation?.languagesSkillsInformation
//    resultRecipient.onResult {
//        createResumeViewModel.saveEducation(
//            it.id, CreateResumeUseCase.PersonalInformation.Education(
//                educationStage = it.educationStage,
//                title = it.title,
//                locationCity = it.locationCity,
//                enterDate = it.enterDate,
//                graduatedDate = it.graduatedDate,
//                faculty = it.faculty,
//                speciality = it.speciality
//            )
//        )
//    }
    Screen(
        title = stringResource(id = R.string.education),
        modifier = Modifier.fillMaxSize(),
        alignment = if (languageSkillList != null) Alignment.Top else Alignment.CenterVertically,
        hasBackButton = true,
        onBack = {
            navigator.navigateUp()
        },
        floatingActionButton = {
            LanguageSkillsComponent().FloatingActionButton(
                navigator = navigator,
                educationList = languageSkillList
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LanguageSkillsComponent().Content(modifier = Modifier
            .fillMaxSize(),
            skillsList = languageSkillList,
            onEdit = { i, language ->
                LanguageSkillsComponent().createOrEdit(
                    navigator = navigator,
                    id = i,
                    skill = CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation(
                        languageName = language.languageName, knowledgeLevel = language.knowledgeLevel
                    )
                )
            })
    }
}