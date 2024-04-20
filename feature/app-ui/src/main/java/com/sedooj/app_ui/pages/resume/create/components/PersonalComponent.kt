package com.sedooj.app_ui.pages.resume.create.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.SocialMedia
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.ui_kit.DateButton
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
    onEducation: (Education) -> Unit,
) {
    EducationList(education = education) {  s ->
        onEducation(s)
    }
}

@Composable
fun EducationList(
    education: List<Education>,
    onEducation: (Education) -> Unit
) {
    education.forEach { edu ->
        EducationComponent(
            onEducation = {
                onEducation(it)
            }
        )
    }
}

@Composable
fun EducationComponent(
    onEducation: (Education)-> Unit
) {
    val education by rememberSaveable { mutableStateOf(Education(
        educationStage = EducationStage.COLLEGE,
        title = "",
        locationCity = "",
        enterDate = "",
        graduatedDate = "",
        faculty = "",
        speciality = ""
    ))}
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
            education.title = it
        },
        value = education.title
    )
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