package com.sedooj.app_ui.pages.resume.create.components.skills.components.languages.edit.data

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.types.LanguageKnowledgeLevelType
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.LanguageKnowledgeConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.SalaryValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import com.sedooj.ui_kit.fields.SalaryField
import java.io.Serializable

enum class LanguageSkillsComponentFields(
    @StringRes
    val fieldName: Int,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    LANGUAGE(
        fieldName = R.string.language,
        readOnly = false,
    ),
    KNOWLEDGE_LEVEL(
        fieldName = R.string.knowledge_level,
        readOnly = true,
        suggestions = listOf(
            CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.A1)),
            CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.A2)),
            CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.B1)),
            CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.B2)),
            CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.C1)),
            CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.C2)),
        )
    ),
}

class EditLanguageSkillsComponent {
    @Composable
    fun Content(
        data: Map<LanguageSkillsComponentFields, FieldValue>,
        onEdit: (LanguageSkillsComponentFields, FieldValue) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        EditLanguageSkillsComponentContent().Content(
            data = data,
            onValueChange = onEdit,
            modifier = modifier
        )
    }

    @Composable
    fun dataMap(initInfo: EditLanguageSkillsComponentData.LanguageSkill): SnapshotStateMap<LanguageSkillsComponentFields, FieldValue> {
        return EditLanguageSkillsComponentData().rememberDataMap(initInfo = initInfo)
    }

    @Composable
    fun FloatingActionButton(
        onSave: (EditLanguageSkillsComponentData.LanguageSkill) -> Unit,
        isDataSaved: Boolean,
        isDataEdited: Boolean,
        data: Map<LanguageSkillsComponentFields, FieldValue>,
        initInfo: EditLanguageSkillsComponentData.LanguageSkill
    ) {
        EditLanguageSkillsComponentContent().FloatingActionButton(
            onSave = onSave,
            isDataSaved = isDataSaved,
            isDataEdited = isDataEdited,
            data = data,
            initInfo = initInfo
        )
    }
}

class EditLanguageSkillsComponentData {
    data class LanguageSkill(
        var id: Int,
        var languageName: String,
        var knowledge: LanguageKnowledgeLevelType,
        var isEdit: Boolean = false,
    ) : Serializable

    @Composable
    fun rememberDataMap(initInfo: LanguageSkill): SnapshotStateMap<LanguageSkillsComponentFields, FieldValue> {
        return remember {
            mutableStateMapOf(
                LanguageSkillsComponentFields.LANGUAGE to
                        if (initInfo.languageName.isBlank()) {
                            TextValue("")
                        } else {
                            TextValue(initInfo.languageName)
                        },
                LanguageSkillsComponentFields.KNOWLEDGE_LEVEL to if (initInfo.knowledge == LanguageKnowledgeLevelType.NOT_SELECTED) {
                    CustomValue(LanguageKnowledgeConvertibleContainer(LanguageKnowledgeLevelType.NOT_SELECTED))
                } else {
                    CustomValue(LanguageKnowledgeConvertibleContainer(initInfo.knowledge))
                }
            )
        }
    }

    @Composable
    fun parseData(
        data: Map<LanguageSkillsComponentFields, FieldValue>,
        initInfo: LanguageSkill,
    ): LanguageSkill {
        val languageName =
            data[LanguageSkillsComponentFields.LANGUAGE]?.asStringValue() ?: initInfo.languageName
        val knowledgeLevel =
            (data[LanguageSkillsComponentFields.KNOWLEDGE_LEVEL]?.asInitialValue() as LanguageKnowledgeConvertibleContainer?)?.value
                ?: initInfo.knowledge
        return LanguageSkill(
            languageName = languageName,
            knowledge = knowledgeLevel,
            id = initInfo.id
        )
    }
}

private class EditLanguageSkillsComponentContent {

    @Composable
    private fun InputTextField(
        field: LanguageSkillsComponentFields,
        value: String,
        onValueChange: (FieldValue) -> Unit,
        modifier: Modifier = Modifier,
        readOnly: Boolean = false,
    ) {
        NotNullableValueTextField(label = field.fieldName, onValueChange = {
            onValueChange(TextValue(it))
        }, value = value, modifier = modifier, readOnly = readOnly)
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Field(
        field: LanguageSkillsComponentFields,
        value: FieldValue,
        onValueChange: (FieldValue) -> Unit,
        modifier: Modifier = Modifier,
        readOnly: Boolean,
    ) {
        var isFocused by remember { mutableStateOf(false) }
        Column {
            if (value is SalaryValue) {
                SalaryField(onFromValueChange = {
                    onValueChange(SalaryValue(from = it, to = value.to))
                }, onToValueChange = {
                    onValueChange(SalaryValue(from = value.from, to = it))
                }, fromValue = value.from, toValue = value.to)
            } else {
                InputTextField(
                    field = field,
                    value = value.asStringValue(),
                    onValueChange = onValueChange,
                    modifier = modifier.onFocusChanged { isFocused = it.isFocused },
                    readOnly = readOnly
                )
                AnimatedVisibility(visible = (isFocused && field.suggestions.isNotEmpty())) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        field.suggestions.forEach {
                            SuggestionChip(
                                onClick = {
                                    onValueChange(it)
                                },
                                label = {
                                    Text(
                                        text = it.value.asStringValue(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center
                                    )
                                },
                                enabled = value.asStringValue() != it.value.asStringValue()
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun Content(
        data: Map<LanguageSkillsComponentFields, FieldValue>,
        onValueChange: (LanguageSkillsComponentFields, FieldValue) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            data.toSortedMap().forEach { (field, value) ->
                Field(
                    field = field,
                    value = value,
                    onValueChange = { onValueChange(field, it) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = field.readOnly
                )
            }
        }
    }

    @Composable
    fun FloatingActionButton(
        onSave: (EditLanguageSkillsComponentData.LanguageSkill) -> Unit,
        isDataSaved: Boolean,
        isDataEdited: Boolean,
        data: Map<LanguageSkillsComponentFields, FieldValue>,
        initInfo: EditLanguageSkillsComponentData.LanguageSkill,
    ) {
        val parsedData =
            EditLanguageSkillsComponentData().parseData(data = data, initInfo = initInfo)
        AnimatedVisibility(
            visible = !isDataSaved && isDataEdited, enter = scaleIn(tween(200)), exit = scaleOut(
                tween(200)
            )
        ) {
            androidx.compose.material3.FloatingActionButton(onClick = {
                onSave(parsedData)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.done)
                )
            }
        }
    }
}