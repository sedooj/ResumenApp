//package com.sedooj.app_ui.pages.resume.create.components.vacancy
//
//import androidx.activity.compose.BackHandler
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.scaleIn
//import androidx.compose.animation.scaleOut
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Done
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.ButtonColors
//import androidx.compose.material3.FabPosition
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.annotation.RootGraph
//import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//import com.sedooj.api.domain.data.resume.usecase.CreateResumeUseCase
//import com.sedooj.app_ui.navigation.config.ConfirmationScreenTransitions
//import com.sedooj.arch.Routes
//import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
//import com.sedooj.ui_kit.FilledButton
//import com.sedooj.ui_kit.R.drawable
//import com.sedooj.ui_kit.R.string
//import com.sedooj.ui_kit.Screen
//
//@Destination<RootGraph>(
//    start = false,
//    route = Routes.CREATE_RESUME_VACANCY,
//    style = ConfirmationScreenTransitions::class
//)
//@Composable
//fun VacancyComponentPage(
//    destinationsNavigator: DestinationsNavigator,
//    createResumeViewModel: CreateResumeViewModel,
//) {
//    var isAlertDialogVisible by remember { mutableStateOf(false) }
//    val vacancy = createResumeViewModel.uiState.collectAsState().value.vacancyInformation
//    var stackType by rememberSaveable { mutableStateOf(vacancy?.stackType) }
//    var platformType by rememberSaveable { mutableStateOf(vacancy?.platformType) }
//    var desiredRole by rememberSaveable { mutableStateOf(vacancy?.desiredRole ?: "") }
//    var desiredSalary by rememberSaveable { mutableStateOf(vacancy?.desiredSalary ?: "") }
//    var busynessType by rememberSaveable { mutableStateOf(vacancy?.busynessType) }
//    var scheduleType by rememberSaveable { mutableStateOf(vacancy?.scheduleType) }
//    var readyForTravelling by rememberSaveable {
//        mutableStateOf(
//            vacancy?.readyForTravelling ?: false
//        )
//    }
//    var isChangesSaved by rememberSaveable { mutableStateOf(vacancy?.isChangesSaved ?: false) }
//    BackHandler {
//        if (!isChangesSaved)
//            isAlertDialogVisible = true
//        else
//            destinationsNavigator.navigateUp()
//    }
//    Screen(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(15.dp),
//        title = stringResource(id = string.vacancy),
//        navigationButton = {
//            IconButton(onClick = {
//                if (!isChangesSaved)
//                    isAlertDialogVisible = true
//                else
//                    destinationsNavigator.navigateUp()
//            }) {
//                Icon(
//                    painter = painterResource(id = drawable.arrow_back),
//                    contentDescription = stringResource(
//                        id = string.go_back
//                    ),
//                    Modifier.size(15.dp)
//                )
//            }
//        },
//        alignment = Alignment.Top,
//        floatingActionButtonPosition = FabPosition.End,
//        floatingActionButton = {
//            AnimatedVisibility(
//                visible = !isChangesSaved,
//                enter = scaleIn(tween(200)),
//                exit = scaleOut(tween(200))
//            ) {
//                FloatingActionButton(onClick = {
//                    if (stackType != null && platformType != null && scheduleType != null) {
//                        createResumeViewModel.updateVacancyInformation(
//                            CreateResumeUseCase.VacancyInformation(
//                                stackType = stackType!!,
//                                platformType = platformType!!,
//                                desiredRole = desiredRole,
//                                desiredSalary = desiredSalary,
//                                busynessType = busynessType,
//                                scheduleType = scheduleType!!,
//                                readyForTravelling = readyForTravelling,
//                                isChangesSaved = true
//                            )
//                        )
//                        isChangesSaved = true
//                    }
//                }) {
//                    Icon(
//                        imageVector = Icons.Filled.Done,
//                        contentDescription = stringResource(id = string.save)
//                    )
//                }
//            }
//        }
//    ) {
//        VacancyComponent(
//            stackType = stackType,
//            platformType = platformType,
//            desiredRole = desiredRole,
//            desiredSalary = desiredSalary,
//            busynessType = busynessType,
//            scheduleType = scheduleType,
//            readyForTravelling = readyForTravelling,
//            onStackSelect = {
//                stackType = it
//                isChangesSaved = false
//            },
//            onPlatformSelect = {
//                platformType = it
//                isChangesSaved = false
//            },
//            onRoleValueChange = {
//                desiredRole = it
//                isChangesSaved = false
//            },
//            onSalaryValueChange = {
//                desiredSalary = it
//                isChangesSaved = false
//            },
//            onBusynessSelect = {
//                busynessType = it
//                isChangesSaved = false
//            },
//            onScheduleSelect = {
//                scheduleType = it
//                isChangesSaved = false
//            },
//            onReadyTravelValueChange = {
//                readyForTravelling = !readyForTravelling
//                isChangesSaved = false
//            }
//        )
//    }
//    if (isAlertDialogVisible)
//        AlertDialog(
//            onDismissRequest = { isAlertDialogVisible = false },
//            confirmButton = {
//                FilledButton(
//                    label = stringResource(id = string.cancel),
//                    onClick = {
//                        isAlertDialogVisible = false
//                    },
//                    modifier = Modifier
//                        .height(40.dp)
//                        .width(70.dp)
//                )
//            },
//            dismissButton = {
//                FilledButton(
//                    label = stringResource(id = string.yes),
//                    onClick = {
//                        isAlertDialogVisible = false
//                        destinationsNavigator.navigateUp()
//                    },
//                    modifier = Modifier
//                        .height(40.dp)
//                        .width(70.dp),
//                    colors = ButtonColors(
//                        containerColor = MaterialTheme.colorScheme.error,
//                        contentColor = MaterialTheme.colorScheme.surfaceContainerHigh,
//                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
//                        disabledContentColor = MaterialTheme.colorScheme.surfaceContainerHigh
//                    ),
//                )
//            },
//            text = {
//                Text(
//                    text = stringResource(id = string.changes_is_not_saved),
//                    color = MaterialTheme.colorScheme.surfaceTint,
//                    overflow = TextOverflow.Ellipsis,
//                )
//            })
//}