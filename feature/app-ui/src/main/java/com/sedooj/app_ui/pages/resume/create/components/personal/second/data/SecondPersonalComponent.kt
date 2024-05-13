package com.sedooj.app_ui.pages.resume.create.components.personal.second.data

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
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.app_ui.pages.resume.create.components.generic.ConvertibleValue
import com.sedooj.app_ui.pages.resume.create.components.generic.CustomValue
import com.sedooj.app_ui.pages.resume.create.components.generic.FieldValue
import com.sedooj.app_ui.pages.resume.create.components.generic.SocialMediaConvertibleContainer
import com.sedooj.app_ui.pages.resume.create.components.generic.TextValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asInitialValue
import com.sedooj.app_ui.pages.resume.create.components.generic.asStringValue
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.NotNullableValueTextField

enum class SecondPageFields(
    @StringRes
    val fieldName: Int,
    val readOnly: Boolean,
    val suggestions: List<CustomValue<ConvertibleValue>> = emptyList(),
) {
    SOCIAL_MEDIA(
        fieldName = R.string.social_media,
        readOnly = true
    ),
    ABOUT_ME(
        fieldName = R.string.about_me,
        readOnly = false
    ),
    PERSONAL_QUALITIES(
        fieldName = R.string.personal_qualities,
        readOnly = false
    ),
}

class SecondPersonalComponent {

    @Composable
    fun dataMap(initInfo: CreateResumeUseCase.PersonalInformation?): SnapshotStateMap<SecondPageFields, FieldValue> {
        return SecondPersonalComponentData().rememberDataMap(initInfo = initInfo)
    }

    @Composable
    fun Content(
        data: Map<SecondPageFields, FieldValue>,
        onValueChange: (SecondPageFields, FieldValue) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        SecondPersonalComponentContent().GetContent(
            data = data,
            onValueChange = onValueChange,
            modifier = modifier
        )
    }

    @Composable
    fun FloatingActionButton(
        onSaved: (SecondPersonalComponentData.EditorSecondPersonal) -> Unit,
        isDataSaved: Boolean, isDataEdited: Boolean,
        data: Map<SecondPageFields, FieldValue>,
        initInfo: CreateResumeUseCase.PersonalInformation?,
    ) {
        SecondPersonalComponentContent().FloatingActionButton(
            onSaved = onSaved,
            isDataSaved = isDataSaved,
            isDataEdited = isDataEdited,
            data = data,
            initInfo = initInfo
        )
    }

}

class SecondPersonalComponentData {

    data class EditorSecondPersonal(
        var socialMedia: List<CreateResumeUseCase.PersonalInformation.SocialMedia>,
        var aboutMe: String,
        var personalQualities: String,
    ) : java.io.Serializable

    @Composable
    fun rememberDataMap(initInfo: CreateResumeUseCase.PersonalInformation?): SnapshotStateMap<SecondPageFields, FieldValue> {
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
                ) else TextValue("")
            )
        }
    }

    @Composable
    fun parseData(
        data: Map<SecondPageFields, FieldValue>,
        initInfo: CreateResumeUseCase.PersonalInformation?,
    ): EditorSecondPersonal {
        val socialMedia =
            (data[SecondPageFields.SOCIAL_MEDIA]?.asInitialValue() as SocialMediaConvertibleContainer?)?.value
                ?: initInfo?.socialMedia
        val aboutMe =
            data[SecondPageFields.ABOUT_ME]?.asStringValue() ?: initInfo?.aboutMe
        val personalQualities =
            data[SecondPageFields.PERSONAL_QUALITIES]?.asStringValue()
                ?: initInfo?.personalQualities

        return EditorSecondPersonal(
            socialMedia = socialMedia ?: emptyList(),
            aboutMe = aboutMe ?: "",
            personalQualities = personalQualities ?: ""
        )
    }


}

private class SecondPersonalComponentContent {

    @Composable
    fun FloatingActionButton(
        onSaved: (SecondPersonalComponentData.EditorSecondPersonal) -> Unit,
        isDataSaved: Boolean, isDataEdited: Boolean,
        data: Map<SecondPageFields, FieldValue>,
        initInfo: CreateResumeUseCase.PersonalInformation?,
    ) {
        val parsedData = SecondPersonalComponentData().parseData(data = data, initInfo = initInfo)
        AnimatedVisibility(
            visible = !isDataSaved && isDataEdited, enter = scaleIn(tween(200)), exit = scaleOut(
                tween(200)
            )
        ) {
            FloatingActionButton(onClick = {
                onSaved(parsedData)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.done)
                )
            }
        }
    }

    @Composable
    private fun InputTextField(
        field: SecondPageFields,
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
        field: SecondPageFields,
        value: FieldValue,
        onValueChange: (FieldValue) -> Unit,
        modifier: Modifier = Modifier,
        readOnly: Boolean,
    ) {
        var isFocused by remember { mutableStateOf(false) }
        Column {
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
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            enabled = value.asStringValue() != it.value.asStringValue()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun GetContent(
        data: Map<SecondPageFields, FieldValue>,
        onValueChange: (SecondPageFields, FieldValue) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            data.toSortedMap().forEach { (field, value) ->
                Field(
                    field = field,
                    value = value,
                    onValueChange = { onValueChange(field, it) },
                    readOnly = field.readOnly,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}