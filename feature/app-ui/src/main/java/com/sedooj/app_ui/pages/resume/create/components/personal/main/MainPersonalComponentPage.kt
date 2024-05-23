package com.sedooj.app_ui.pages.resume.create.components.personal.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.sedooj.app_ui.navigation.config.SlideScreenTransitions
import com.sedooj.app_ui.pages.resume.create.components.personal.main.data.MainPersonalPageContent
import com.sedooj.app_ui.pages.resume.create.components.personal.main.data.parseMainPersonalData
import com.sedooj.app_ui.pages.resume.create.components.personal.main.data.rememberMainPersonalDataMap
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.components.FloatingSaveButton
import com.sedooj.ui_kit.components.LostDataAlert
import com.sedooj.ui_kit.screens.Screen


@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_PERSONAL_MAIN,
    style = SlideScreenTransitions::class
)
@Composable
fun MainPersonalComponentPage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    var isLostDataAlertShow by remember { mutableStateOf(false) }
    var isDataSaved by remember { mutableStateOf(false) }
    var isDataEdited by remember { mutableStateOf(false) }
    BackHandler {}
    val personal = createResumeViewModel.uiState.collectAsState().value.personalInformation
    val data = rememberMainPersonalDataMap(initInfo = personal)
    Screen(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        title = stringResource(id = R.string.personal_information),
        hasBackButton = true,
        onBack = {
            if (isDataEdited && !isDataSaved)
                isLostDataAlertShow = true
            else
                destinationsNavigator.navigateUp()
        },
        showAlert = isLostDataAlertShow,
        alertDialog = {
            LostDataAlert(
                onDismiss = { isLostDataAlertShow = false },
                onConfirm = {
                    isLostDataAlertShow = false
                    destinationsNavigator.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        alignment = Alignment.Top,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            val parsedData = parseMainPersonalData(data = data, initInfo = personal)
            FloatingSaveButton(
                onSave = {
                    createResumeViewModel.updatePersonalInformation(
                        input = CreateResumeUseCase.PersonalInformation(
                            firstName = parsedData.firstName,
                            secondName = parsedData.secondName,
                            thirdName = parsedData.thirdName,
                            dateOfBirth = parsedData.dateOfBirth,
                            city = parsedData.city,
                            residenceCountry = parsedData.residenceCountry,
                            genderType = parsedData.genderType,
                            maritalStatus = parsedData.maritalStatus,
                            education = personal?.education ?: emptyList(),
                            hasChild = parsedData.hasChild,
                            email = personal?.email ?: "",
                            aboutMe = personal?.aboutMe,
                            personalQualities = personal?.personalQualities ?: ""
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
        MainPersonalPageContent(
            data = data,
            onValueChange = { field, value ->
                data[field] = value
                isDataEdited = true
                isDataSaved = false
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}