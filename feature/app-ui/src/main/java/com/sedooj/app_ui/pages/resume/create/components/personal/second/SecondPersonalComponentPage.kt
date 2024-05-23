package com.sedooj.app_ui.pages.resume.create.components.personal.second

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.api.domain.data.types.GenderType
import com.sedooj.api.domain.data.types.MaritalStatus
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.personal.second.data.SecondPersonalPageContent
import com.sedooj.app_ui.pages.resume.create.components.personal.second.data.parseSecondPersonalData
import com.sedooj.app_ui.pages.resume.create.components.personal.second.data.rememberSecondPersonalDataMap
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.FloatingSaveButton
import com.sedooj.ui_kit.components.LostDataAlert
import com.sedooj.ui_kit.screens.Screen


@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_PERSONAL_SECONDARY,
    style = SlideScreenTransitions::class
)
@Composable
fun SecondPersonalComponentPage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    var isDataEdited by remember { mutableStateOf(false) }
    var isDataSaved by remember { mutableStateOf(false) }
    BackHandler {}
    val personal = createResumeViewModel.uiState.collectAsState().value.personalInformation
    val data = rememberSecondPersonalDataMap(initInfo = personal)

    Screen(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        title = stringResource(id = R.string.personal_information),
        hasBackButton = true,
        onBack = {
            if (isDataEdited && !isDataSaved)
                isAlertDialogVisible = true
            else
                destinationsNavigator.navigateUp()
        },
        alignment = Alignment.Top,
        showAlert = isAlertDialogVisible,
        alertDialog = {
            LostDataAlert(onDismiss = { isAlertDialogVisible = false }, onConfirm = {
                isAlertDialogVisible = false
                destinationsNavigator.navigateUp()
            })
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            val parsedData = parseSecondPersonalData(data = data, initInfo = personal)
            FloatingSaveButton(
                onSave = {
                    createResumeViewModel.updatePersonalInformation(
                        input = CreateResumeUseCase.PersonalInformation(
                            firstName = personal?.firstName ?: "",
                            secondName = personal?.secondName ?: "",
                            thirdName = personal?.thirdName ?: "",
                            dateOfBirth = personal?.dateOfBirth ?: "",
                            city = personal?.city ?: "",
                            residenceCountry = personal?.residenceCountry ?: "",
                            genderType = personal?.genderType ?: GenderType.NOT_SELECTED,
                            maritalStatus = personal?.maritalStatus
                                ?: MaritalStatus.NOT_SELECTED,
                            education = personal?.education ?: emptyList(),
                            hasChild = personal?.hasChild ?: false,
                            email = parsedData.email,
                            aboutMe = parsedData.aboutMe,
                            personalQualities = parsedData.personalQualities
                        )
                    )
                    isDataSaved = true
                    destinationsNavigator.navigateUp()
                },
                isDataSaved = isDataSaved,
                isDataEdited = isDataEdited
            )
        }
    ) {
        SecondPersonalPageContent(
            data = data,
            onValueChange = { field, value ->
                data[field] = value
                isDataEdited = true
                isDataSaved = false
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}