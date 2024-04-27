package com.sedooj.app_ui.pages.resume.create.components.personal.main

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
import com.sedooj.app_ui.navigation.config.ScreensTransitions
import com.sedooj.app_ui.pages.resume.create.components.MainComponent
import com.sedooj.arch.Routes
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.Screen

enum class Field(
    @StringRes
    val label: Int,
    val suggestions: List<Int> = emptyList(),
) {
    FIRSTNAME(R.string.firstname), SECONDNAME(R.string.secondname), GENDER(
        R.string.gender_picker,
        listOf(R.string.)
    )
}

@Composable
fun rememberDataMap(initInfo: CreateResumeUseCase.PersonalInformation?): SnapshotStateMap<Field, String> {
    return remember {
        mutableStateMapOf(
            Field.FIRSTNAME to (initInfo?.firstName ?: ""),
            Field.SECONDNAME to (initInfo?.secondName ?: ""),
        )
    }
}

@Destination<RootGraph>(
    start = false,
    route = Routes.CREATE_RESUME_PERSONAL_MAIN,
    style = ScreensTransitions::class
)
@Composable
fun MainPersonalComponentPage(
    destinationsNavigator: DestinationsNavigator,
    createResumeViewModel: CreateResumeViewModel,
) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    BackHandler {
//        if (!isChangesSaved)
        isAlertDialogVisible = true
//        else
//            destinationsNavigator.navigateUp()
    }
    val personal = createResumeViewModel.uiState.collectAsState().value.personalInformation
    val data = rememberDataMap(personal)
    var firstName by rememberSaveable { mutableStateOf(personal?.firstName) }
    val secondName = rememberSaveable { mutableStateOf(personal?.secondName) }
    val thirdName = rememberSaveable { mutableStateOf(personal?.thirdName) }
    val dateOfBirth = rememberSaveable { mutableStateOf(personal?.dateOfBirth) }
    val city = rememberSaveable { mutableStateOf(personal?.city) }
    val residenceCountry = rememberSaveable { mutableStateOf(personal?.residenceCountry) }
    val genderType = rememberSaveable { mutableStateOf(personal?.genderType) }
    val maritalStatus = rememberSaveable { mutableStateOf(personal?.maritalStatus) }
    val education =
        remember { mutableStateListOf<CreateResumeUseCase.PersonalInformation.Education>() }
    val hasChild = rememberSaveable { mutableStateOf(personal?.hasChild) }
    val socialMedia = rememberSaveable { mutableStateOf(personal?.socialMedia) }
    val aboutMe = rememberSaveable { mutableStateOf(personal?.aboutMe) }
    val personalQualities = rememberSaveable { mutableStateOf(personal?.personalQualities) }
    Screen(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        title = stringResource(id = R.string.vacancy),
        navigationButton = {
            IconButton(onClick = {
//                if (!isChangesSaved)
                isAlertDialogVisible = true
//                else
//                    destinationsNavigator.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(
                        id = R.string.go_back
                    ),
                    Modifier.size(15.dp)
                )
            }
        },
        alignment = Alignment.Top,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
//            AnimatedVisibility(
//                visible = !isChangesSaved,
//                enter = scaleIn(tween(200)),
//                exit = scaleOut(tween(200))
//            ) {
//                FloatingActionButton(onClick = {
//
//                        isChangesSaved = true
//
//                }) {
//                    Icon(
//                        imageVector = Icons.Filled.Done,
//                        contentDescription = stringResource(id = R.string.save)
//                    )
//                }
//            }
        }
    ) {
        MainComponent(
            data = data,
            onValueChange = { field, value ->
                data[field] = value
            },
            modifier = modifier,
        )
        //TODO()
    }
}