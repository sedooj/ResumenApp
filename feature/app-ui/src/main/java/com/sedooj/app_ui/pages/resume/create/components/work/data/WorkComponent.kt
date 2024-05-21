package com.sedooj.app_ui.pages.resume.create.components.work.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
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
import com.ramcosta.composedestinations.generated.destinations.WorkEditorDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.ui_kit.R
import java.io.Serializable


data class EditWork(
    var id: Int,
    var company: String,
    var kindOfActivity: String,
    var enterJobDate: String,
    var quitJobDate: String,
    var currentlyWorking: Boolean,
    var isEdit: Boolean = false,
) : Serializable

fun createOrEdit(
    navigator: DestinationsNavigator,
    id: Int,
    work: CreateResumeUseCase.WorkExperienceInformation? = null,
) {
    if (work == null)
        navigator.navigate(
            WorkEditorDestination(
                EditWork(
                    id = id,
                    company = "",
                    kindOfActivity = "",
                    enterJobDate = "",
                    quitJobDate = "",
                    currentlyWorking = false,
                )
            )
        ) {
            launchSingleTop = true
        }
    else
        navigator.navigate(
            WorkEditorDestination(
                EditWork(
                    id = id,
                    isEdit = true,
                    company = work.companyName,
                    kindOfActivity = work.kindOfActivity,
                    enterJobDate = work.gotJobDate,
                    quitJobDate = work.quitJobDate,
                    currentlyWorking = work.isCurrentlyWorking
                )
            )
        ) {
            launchSingleTop = true
        }
}


@Composable
fun WorkListContent(
    workExperienceList: List<CreateResumeUseCase.WorkExperienceInformation>?,
    onEdit: (Int, CreateResumeUseCase.WorkExperienceInformation) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (workExperienceList.isNullOrEmpty())
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
            Text(
                text = stringResource(id = R.string.put_information_about_work_experience),
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
            workExperienceList.forEachIndexed { index, work ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = work.companyName,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }, supportingContent = {

                    }, leadingContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.work_experience),
                            contentDescription = work.companyName,
                            modifier = Modifier.size(40.dp)
                        )
                    }, modifier = modifier.clickable(onClick = dropUnlessResumed {
                        onEdit(
                            index, work
                        )
                    })
                )
            }
        }
}
