package com.sedooj.app_ui.pages.resume.create.components

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.SocialMedia
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.ui_kit.DateButton
import com.sedooj.ui_kit.FilledButton
import com.sedooj.ui_kit.MenuButton
import com.sedooj.ui_kit.NotNullableValueTextField
import com.sedooj.ui_kit.R

@Composable
fun MainComponent(
    firstName: String?,
    secondName: String?,
    thirdName: String?,
    dateOfBirth: String?,
    city: String?,
    residenceCountry: String?,
    genderType: GenderType?,
    maritalStatus: MaritalStatus?,
    onDate: (String?) -> Unit,
    onGenderType: (GenderType) -> Unit,
    onMaritalType: (MaritalStatus) -> Unit,
) {
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
) {
    EducationList(education = education) { i, s ->
        onEducation(i, s)
    }
}

@Composable
fun EducationList(
    education: List<Education>,
    onEducation: (Int, Education) -> Unit,
) {
    education.forEachIndexed { i, edu ->
        EducationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .animateContentSize(),
            education = edu,
            onEducation = { education ->
                onEducation(i, education)
            },
            onRemove = {

            }
        )
    }
    FilledButton(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(id = R.string.add_education_institution)
    ) {
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
}

@Composable
fun EducationComponent(
    modifier: Modifier = Modifier,
    education: Education,
    onEducation: (Education) -> Unit,
    onRemove: () -> Unit,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = education.title.ifEmpty { stringResource(id = R.string.edu_organisation_name) },
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                NotNullableValueTextField(
                    label = R.string.edu_organisation_name,
                    onValueChange = {
                        onEducation(
                            Education(
                                educationStage = education.educationStage,
                                title = it,
                                locationCity = education.locationCity,
                                enterDate = education.enterDate,
                                graduatedDate = education.graduatedDate,
                                faculty = education.faculty,
                                speciality = education.speciality

                            )
                        )
                        education.title = it
                    },
                    value = education.title
                )
            }

        }
        IconButton(onClick = { onRemove() }, modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(
                painterResource(id = R.drawable.trash),
                contentDescription = stringResource(id = R.string.remove_education_institution),
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.error
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