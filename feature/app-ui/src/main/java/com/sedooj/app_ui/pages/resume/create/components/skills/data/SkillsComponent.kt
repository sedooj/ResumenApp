package com.sedooj.app_ui.pages.resume.create.components.skills.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.generated.destinations.SkillsLanguagesDestination
import com.ramcosta.composedestinations.generated.destinations.SkillsProgrammingLanguagesDestination
import com.ramcosta.composedestinations.spec.Direction
import com.sedooj.ui_kit.R

enum class SkillsComponentList(
    val title: Int,
    val subTitle: Int?,
    val icon: Int,
    val direction: Direction,
) {
    LANGUAGES(
        title = R.string.skills_languages,
        subTitle = null,
        icon = R.drawable.languages,
        direction = SkillsLanguagesDestination
    ),
    PROGRAMMING_LANGUAGES(
        title = R.string.skills_programming_languages,
        subTitle = null,
        icon = R.drawable.code_brackets,
        direction = SkillsProgrammingLanguagesDestination
    )
}

@Composable
fun SkillsListPageContent(
    onSelect: (Direction) -> Unit,
    modifier: Modifier = Modifier,
) {
    SkillsComponentList.entries.forEach {
        ListItem(
            headlineContent = {
                Text(text = stringResource(id = it.title))
            }, supportingContent = {
                if (it.subTitle != null)
                    Text(text = stringResource(id = it.subTitle))
            }, leadingContent = {
                Icon(
                    painter = painterResource(id = it.icon),
                    contentDescription = stringResource(id = it.title),
                    modifier = Modifier.size(25.dp)
                )
            }, modifier = modifier.clickable(onClick = {
                onSelect(it.direction)
            })
        )
    }
}