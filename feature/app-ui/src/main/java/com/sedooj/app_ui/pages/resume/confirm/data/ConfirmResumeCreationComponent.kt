package com.sedooj.app_ui.pages.resume.confirm.data

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.template.TemplateEntity
import com.sedooj.ui_kit.R.string

@Composable
fun ConfirmResumeCreationContent(
    onSelect: (Int, TemplateEntity) -> Unit,
    modifier: Modifier = Modifier,
    selectedTemplateIndexValue: Int,
    selectedTemplateEntity: TemplateEntity?,
) {
//    val templateList = getTemplateList()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = stringResource(id = string.choose_template),
//            textAlign = TextAlign.Center,
//            fontWeight = FontWeight.Bold
//        )
        UnavailableTemplatesComponent(modifier = Modifier.fillMaxWidth())
//        ChooseTemplateComponent(
//            onSelect = onSelect,
//            templateList = templateList,
//            selectedTemplateIndexValue = selectedTemplateIndexValue,
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.primary,
//                    shape = RoundedCornerShape(10.dp)
//                ),
//            selectedTemplateEntity = selectedTemplateEntity
//        )
    }
}

/**
 * TODO: Add method to get the template list from server
 * */
private fun getTemplateList(): List<TemplateEntity> {
    return listOf(
        TemplateEntity(title = "augue", preview = 0, isPremium = false),
        TemplateEntity(title = "elitr", preview = 0, isPremium = false),
        TemplateEntity(title = "pretium", preview = 0, isPremium = false),
        TemplateEntity(title = "dictas", preview = 0, isPremium = false),
        TemplateEntity(title = "ridens", preview = 0, isPremium = false),
        TemplateEntity(title = "quod", preview = 0, isPremium = false),
        TemplateEntity(title = "theophrastus", preview = 0, isPremium = false)
    )
}

@Composable
private fun UnavailableTemplatesComponent(modifier: Modifier = Modifier) {
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
    Text(
        text = "Выбор шаблонов в данный момент не доступен, используется стандартный шаблон",
        textAlign = TextAlign.Center,
        modifier = modifier,
        color = MaterialTheme.colorScheme.error
    )
//    }
}

@Composable
private fun ChooseTemplateComponent(
    onSelect: (Int, TemplateEntity) -> Unit,
    modifier: Modifier = Modifier,
    templateList: List<TemplateEntity>,
    selectedTemplateIndexValue: Int,
    selectedTemplateEntity: TemplateEntity?,
) {
    val selectedColor = MaterialTheme.colorScheme.primary
    val notSelectedColor = MaterialTheme.colorScheme.inverseSurface
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(state = scrollState),
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                templateList.forEachIndexed { index, template ->
                    TemplateItem(
                        templateEntity = template,
                        modifier = Modifier
                            .size(250.dp, 300.dp)
                            .padding(10.dp)
                            .border(
                                width = 1.dp,
                                color = if (selectedTemplateIndexValue == index) selectedColor else notSelectedColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(enabled = index != selectedTemplateIndexValue, onClick = {
                                onSelect(index, template)
                            }),
                        isSelected = index == selectedTemplateIndexValue
                    )
                }
            }
        }
//        FlowRow(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(15.dp),
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//            horizontalArrangement = Arrangement.spacedBy(10.dp)
//        ) {
//
//        }
    }
}

@Composable
private fun TemplateItem(
    templateEntity: TemplateEntity,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
) {
//    val animatedContentSize = animateDpAsState(targetValue = if (!isSelected) 15.dp else 20.dp)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .size(animatedContentSize.value), contentAlignment = Alignment.Center
//        ) {
        Text(
            text = templateEntity.title,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            AnimatedVisibility(
                visible = isSelected,
                enter = scaleIn(tween(200)),
                exit = scaleOut(tween(50))
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(5.dp)
                        ),
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(id = string.chosen_template) + templateEntity.title
                )
            }
        }
//        }
    }
}