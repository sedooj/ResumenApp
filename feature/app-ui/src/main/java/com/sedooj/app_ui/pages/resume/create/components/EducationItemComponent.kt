package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase.PersonalInformation.Education
import com.sedooj.api.domain.data.types.EducationStage
import com.sedooj.ui_kit.fields.FilledButton
import com.sedooj.ui_kit.R

@Deprecated("Old version of EducationItem", level = DeprecationLevel.WARNING)
@Composable
fun EducationItemComponent(
    modifier: Modifier = Modifier,
    education: Education,
    shape: Shape = RoundedCornerShape(10.dp),
    elevation: CardElevation = CardDefaults.cardElevation(),
    onEditEducation: () -> Unit,
    onDropEducation: () -> Unit,
    border: BorderStroke? = null,
) {
    val educationCorrect = education.checkCorrect()
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
        Box(modifier = modifier.padding(15.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    5.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = shape,
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.surfaceTint,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        disabledContentColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    elevation = elevation,
                    border = border,
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                10.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.education),
                                        contentDescription = stringResource(
                                            id = R.string.education
                                        ),
                                        modifier = Modifier.size(50.dp)
                                    )
                                }
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(3f),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.spacedBy(
                                            10.dp,
                                            alignment = Alignment.CenterVertically
                                        )
                                    ) {
                                        Text(
                                            text = education.title.ifEmpty { stringResource(id = R.string.edu_organisation_name) },
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${stringResource(id = R.string.education_stage)}: ${education.educationStage.educationType.title}",
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Normal
                                        )
                                        if (education.enterDate.isNotBlank())
                                            Row {
                                                Text(
                                                    text = if (educationCorrect == 0) education.enterDate else "",
                                                    maxLines = 1,
                                                    textAlign = TextAlign.Center,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontWeight = FontWeight.Light
                                                )
                                                Text(
                                                    text = if (education.graduatedDate.isNotEmpty()) " - " else "",
                                                    fontWeight = FontWeight.Light
                                                )
                                                Text(
                                                    text = education.graduatedDate.ifEmpty { "" },
                                                    maxLines = 1,
                                                    textAlign = TextAlign.Center,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontWeight = FontWeight.Light
                                                )
                                            }
                                        Text(
                                            text = "${stringResource(id = R.string.faculty)}:  ${
                                                education.faculty.ifEmpty {
                                                    stringResource(
                                                        id = R.string.not_specified
                                                    )
                                                }
                                            }",
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Normal
                                        )
                                        Text(
                                            text = "${stringResource(id = R.string.speciality)}:  ${
                                                education.speciality.ifEmpty {
                                                    stringResource(
                                                        id = R.string.not_specified_femenine
                                                    )
                                                }
                                            }",
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            )

                        }

                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledButton(
                        label = stringResource(id = R.string.delete),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(35.dp),
                        onClick = {
                            onDropEducation()
                        },
                        icon = painterResource(id = R.drawable.trash),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    )
                    FilledButton(
                        label = stringResource(id = R.string.edit),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(35.dp),
                        onClick = {
                            onEditEducation()
                        })
                }
                if (educationCorrect != 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = educationCorrect),
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }

        if (educationCorrect != 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = stringResource(
                        id = R.string.has_errors_in_education
                    ),
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.TopStart)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceDim,
                            shape = CircleShape
                        ),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

}

private fun Education.checkCorrect(): Int {
    return if (
        this.title.isBlank()
        || this.educationStage == EducationStage.NOT_SPECIFIED
        || this.faculty.isBlank()
        || this.speciality.isBlank()
        || this.locationCity.isBlank()
    ) R.string.not_all_field_are_filled else 0
}

@Composable
private fun EducationItemButtons(
    onEditEducation: () -> Unit,
    onDropEducation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.End
        ),
        verticalAlignment = Alignment.Bottom
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_pen),
                contentDescription = stringResource(
                    id = R.string.edit_education
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.trash),
                contentDescription = stringResource(
                    id = R.string.drop_resume
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }
    }
}