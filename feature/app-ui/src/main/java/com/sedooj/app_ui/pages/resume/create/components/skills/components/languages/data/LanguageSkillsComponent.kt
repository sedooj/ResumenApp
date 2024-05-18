package com.sedooj.app_ui.pages.resume.create.components.skills.components.languages.data

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
import com.ramcosta.composedestinations.generated.destinations.SkillsEditLanguagesDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.LanguageKnowledgeLevelType
import com.sedooj.app_ui.pages.resume.create.components.skills.components.languages.edit.data.LanguageSkill
import com.sedooj.ui_kit.R


fun addLanguageOrEdit(
    navigator: DestinationsNavigator,
    id: Int,
    skill: CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation? = null,
) {
    if (skill == null) {
        navigator.navigate(
            SkillsEditLanguagesDestination(
                LanguageSkill(
                    languageName = "",
                    knowledge = LanguageKnowledgeLevelType.NOT_SELECTED,
                    id = id
                )
            )
        ) {
            launchSingleTop = true
        }
    } else {
        navigator.navigate(
            SkillsEditLanguagesDestination(
                LanguageSkill(
                    languageName = skill.languageName,
                    knowledge = skill.knowledgeLevel,
                    id = id,
                    isEdit = true
                )
            )
        ) {
            launchSingleTop = true
        }
    }
}


@Composable
fun LanguageSkillsPageContent(
    skillsList: List<CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation>?,
    onEdit: (Int, CreateResumeUseCase.SkillsInformation.LanguageSkillsInformation) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (skillsList.isNullOrEmpty())
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
            Text(
                text = stringResource(id = R.string.put_information_about_languages),
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
            skillsList.forEachIndexed { index, language ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = language.languageName,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }, supportingContent = {

                    }, leadingContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.languages),
                            contentDescription = language.languageName,
                            modifier = Modifier.size(40.dp)
                        )
                    }, modifier = modifier.clickable(onClick = dropUnlessResumed {
                        onEdit(
                            index, language
                        )
                    })
                )
            }
        }
}