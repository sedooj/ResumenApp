package com.sedooj.app_ui.pages.resume.create.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.SocialMedia
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.app_ui.pages.resume.create.components.personal.main.Field
import com.sedooj.ui_kit.DateButton
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.MenuButton
import com.sedooj.ui_kit.NotNullableValueTextField
import com.sedooj.ui_kit.R

@Composable
fun FieldField(
    field: Field,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    NotNullableValueTextField(
        label = field.label,
        onValueChange = onValueChange,
        value = value,
        modifier = modifier,
    )
}

@Composable
fun MainComponent(
    data: Map<Field, String>,
    firstName: String?,
    secondName: String?,
    thirdName: String?,
    dateOfBirth: String?,
    city: String?,
    residenceCountry: String?,
    genderType: GenderType?,
    maritalStatus: MaritalStatus?,
    onValueChange: (Field, String) -> Unit
    onDate: (String?) -> Unit,
    onGenderType: (GenderType) -> Unit,
    onMaritalType: (MaritalStatus) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        data.forEach { (field, value) ->
            FieldField(
                field = field,
                value = value,
                onValueChange = { onValueChange(field, it) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
    NotNullableValueTextField(
        label = R.string.firstname,
        onValueChange = {
            TODO()
        }, value = firstName
    )
    NotNullableValueTextField(
        label = R.string.secondname,
        onValueChange = {
            TODO()
        }, value = secondName
    )
    NotNullableValueTextField(
        label = R.string.thirdname,
        onValueChange = {
            TODO()
        }, value = thirdName
    )
    DatePickerComponent(
        dateOfBirth = dateOfBirth,
        onDate = {
            onDate(it)
        }
    )
    NotNullableValueTextField(
        label = R.string.city,
        onValueChange = {
            TODO()
        }, value = city
    )
    NotNullableValueTextField(
        label = R.string.residence_country,
        onValueChange = {
            TODO()
        }, value = residenceCountry
    )
    GenderComponent(genderType = genderType,
        onGenderType = {
            onGenderType(it)
        }
    )
    MaritalComponent(
        maritalType = maritalStatus,
        genderType = genderType,
        onMaritalType = {
            onMaritalType(it)
        }
    )
}

@Composable
fun SecondComponent(
    education: List<Education>,
    hasChild: Boolean?,
    socialMedia: List<SocialMedia>?,
    aboutMe: String?,
    personalQualities: String?,
    onEducation: (Int, Education) -> Unit,
    onDropEducation: (Int) -> Unit,
) {
    EducationList(education = education,
        onEducation = { i, s ->
            onEducation(i, s)
        },
        onDropEducation = {
            onDropEducation(it)
        }
    )
}

@Composable
fun EducationList(
    education: List<Education>,
    onEducation: (Int, Education) -> Unit,
    onDropEducation: (Int) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Text(
                    text = stringResource(id = R.string.education),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = education.isNotEmpty(),
                        enter = scaleIn(tween(200)),
                        exit = scaleOut(tween(150))
                    ) {
                        IconButton(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .rotate(if (!isExpanded) 270f else 0f)
                                .size(25.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

            }
            HorizontalDivider()
            education.forEachIndexed { i, edu ->
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn(tween(400)) + expandVertically(tween(200)),
                    exit = fadeOut(tween(400)) + shrinkVertically(tween(200))
                ) {
                    EducationItemComponent(
                        education = edu,
                        onEditEducation = { },
                        onDropEducation = {
                            if (education.size < 2)
                                isExpanded = false
                            onDropEducation(i)
                        },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

            FilledButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.add_education_institution),
                onClick = {
                    onEducation(
                        if (education.isEmpty()) 0 else education.size + 1,
                        Education(
                            educationStage = EducationStage.NOT_SPECIFIED,
                            title = "",
                            locationCity = "",
                            enterDate = "",
                            graduatedDate = "",
                            faculty = "",
                            speciality = ""

                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun GenderComponent(
    genderType: GenderType?,
    onGenderType: (GenderType) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        title = if (genderType != null) stringResource(id = genderType.title) else "",
        label = stringResource(id = R.string.gender_picker),
        onClick = { isExpanded = true },
        isChecked = genderType != null,
        isExpanded = isExpanded
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            GenderType.entries.forEach { gender ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = gender.title),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            maxLines = 1
                        )
                    },
                    onClick = {
                        onGenderType(gender)
                        isExpanded = false
                    },
                    enabled = genderType != gender
                )
            }
        }
    }
}

@Composable
private fun MaritalComponent(
    maritalType: MaritalStatus?,
    onMaritalType: (MaritalStatus) -> Unit,
    genderType: GenderType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        title = if (maritalType != null) stringResource(id = maritalType.title) else "",
        label = stringResource(id = R.string.marital_picker),
        onClick = {
            if (genderType != null) isExpanded = true else Toast.makeText(
                context,
                "Выберите пол",
                Toast.LENGTH_SHORT
            ).show()
        },
        isChecked = maritalType != null,
        isExpanded = isExpanded
    ) {

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            MaritalStatus.entries.forEach { marital ->
                if (genderType != null) {
                    if (genderType == GenderType.MALE) {
                        if (marital == MaritalStatus.MARRIED || marital == MaritalStatus.NOT_MARRIED)
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(id = marital.title),
                                        textAlign = TextAlign.Center,
                                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                        maxLines = 1
                                    )
                                },
                                onClick = {
                                    onMaritalType(marital)
                                    isExpanded = false
                                },
                                enabled = maritalType != marital
                            )
                    } else if (genderType == GenderType.FEMALE) {
                        if (marital == MaritalStatus.FEMALE_MARRIED || marital == MaritalStatus.FEMALE_NOT_MARRIED)
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(id = marital.title),
                                        textAlign = TextAlign.Center,
                                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                        maxLines = 1
                                    )
                                },
                                onClick = {
                                    onMaritalType(marital)
                                    isExpanded = false
                                },
                                enabled = maritalType != marital
                            )
                    }
                }
            }
        }
    }
}

@Composable
private fun DatePickerComponent(
    dateOfBirth: String?,
    onDate: (String?) -> Unit,
) {
    DateButton(
        modifier = Modifier.fillMaxWidth(),
        title = dateOfBirth ?: "",
        label = stringResource(id = R.string.birth_date),
        onEnterDate = {
            onDate(it)
        }
    )
}