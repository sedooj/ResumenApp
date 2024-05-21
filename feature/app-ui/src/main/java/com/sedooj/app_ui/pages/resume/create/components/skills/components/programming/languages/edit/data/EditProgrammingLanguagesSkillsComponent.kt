package com.sedooj.app_ui.pages.resume.create.components.skills.components.programming.languages.edit.data

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.SalaryValue
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.NotNullableValueTextField
import com.sedooj.ui_kit.fields.SalaryField
import java.io.Serializable

enum class ProgrammingLanguageSkillsComponentFields(
    @StringRes
    val fieldName: Int,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    PROGRAMMING_LANGUAGE(
        fieldName = R.string.programming_language,
        readOnly = false,
    )
}

data class ProgrammingLanguageSkill(
    var id: Int,
    var languageName: String,
    var isEdit: Boolean = false,
) : Serializable

@Composable
fun rememberEditProgrammingLanguageDataMap(initInfo: ProgrammingLanguageSkill): SnapshotStateMap<ProgrammingLanguageSkillsComponentFields, FieldValue> {
    return remember {
        mutableStateMapOf(
            ProgrammingLanguageSkillsComponentFields.PROGRAMMING_LANGUAGE to
                    if (initInfo.languageName.isBlank()) {
                        TextValue("")
                    } else {
                        TextValue(initInfo.languageName)
                    }
        )
    }
}

@Composable
fun parseEditProgrammingLanguageData(
    data: Map<ProgrammingLanguageSkillsComponentFields, FieldValue>,
    initInfo: ProgrammingLanguageSkill,
): ProgrammingLanguageSkill {
    val languageName =
        data[ProgrammingLanguageSkillsComponentFields.PROGRAMMING_LANGUAGE]?.asStringValue()
            ?: initInfo.languageName
    return ProgrammingLanguageSkill(
        languageName = languageName,
        id = initInfo.id
    )
}


@Composable
private fun InputTextField(
    field: ProgrammingLanguageSkillsComponentFields,
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
    field: ProgrammingLanguageSkillsComponentFields,
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
fun EditProgrammingLanguagePageContent(
    data: Map<ProgrammingLanguageSkillsComponentFields, FieldValue>,
    onEdit: (ProgrammingLanguageSkillsComponentFields, FieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        data.toSortedMap().forEach { (field, value) ->
            Field(
                field = field,
                value = value,
                onValueChange = { onEdit(field, it) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = field.readOnly
            )
        }
    }
}