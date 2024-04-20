package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.ScheduleType
import com.sedooj.api.domain.data.types.StackType
import com.sedooj.ui_kit.CheckButton
import com.sedooj.ui_kit.MenuButton
import com.sedooj.ui_kit.NotNullableValueTextField
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.SalaryTextField


/*
        var readyForTravelling: Boolean,
* */
@Composable
fun VacancyComponent(
    stackType: StackType?,
    platformType: PlatformType?,
    desiredRole: String,
    desiredSalary: String,
    busynessType: BusynessType?,
    scheduleType: ScheduleType?,
    readyForTravelling: Boolean,
    onStackSelect: (StackType) -> Unit,
    onPlatformSelect: (PlatformType) -> Unit,
    onRoleValueChange: (String) -> Unit,
    onSalaryValueChange: (String) -> Unit,
    onBusynessSelect: (BusynessType) -> Unit,
    onScheduleSelect: (ScheduleType) -> Unit,
    onReadyTravelValueChange: () -> Unit,
) {
    StackTypeMenu(
        onSelect = {
            onStackSelect(it)
        },
        selectedType = stackType
    )
    PlatformTypeMenu(
        onSelect = {
            onPlatformSelect(it)
        },
        selectedType = platformType
    )
    BusynessTypeMenu(
        onSelect = { onBusynessSelect(it) },
        selectedType = busynessType
    )
    ScheduleTypeMenu(
        onSelect = {
            onScheduleSelect(it)
        },
        selectedType = scheduleType
    )
    NotNullableValueTextField(
        label = R.string.desired_role,
        onValueChange = {
            onRoleValueChange(it)
        },
        value = desiredRole
    )
    SalaryTextField(
        label = R.string.desired_salary,
        onValueChange = {
            onSalaryValueChange(it)
        },
        value = desiredSalary
    )
    CheckButton(
        label = stringResource(id = R.string.ready_for_travelling),
        onClick = { onReadyTravelValueChange() },
        isChecked = readyForTravelling
    )
}

@Composable
private fun ScheduleTypeMenu(
    onSelect: (ScheduleType) -> Unit,
    selectedType: ScheduleType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) stringResource(id = R.string.schedule_picker) else stringResource(
            id = R.string.schedule_picked
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null,
        title = if (selectedType != null) stringResource(id = selectedType.title) else "",
        isExpanded = isExpanded
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            ScheduleType.entries.forEach { scheduleType ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = scheduleType.title),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            maxLines = 1
                        )
                    },
                    onClick = {
                        onSelect(scheduleType)
                        isExpanded = false
                    },
                    enabled = selectedType != scheduleType
                )
            }
        }
    }
}

@Composable
private fun BusynessTypeMenu(
    onSelect: (BusynessType) -> Unit,
    selectedType: BusynessType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) stringResource(id = R.string.busyness_picker) else stringResource(
            id = R.string.busyness_picked
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null,
        title = if (selectedType != null) stringResource(id = selectedType.title) else "",
        isExpanded = isExpanded
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            BusynessType.entries.forEach { busynessType ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = busynessType.title),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            maxLines = 1
                        )
                    },
                    onClick = {
                        onSelect(busynessType)
                        isExpanded = false
                    },
                    enabled = selectedType != busynessType
                )
            }
        }
    }

}

@Composable
private fun StackTypeMenu(
    onSelect: (StackType) -> Unit,
    selectedType: StackType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) stringResource(id = R.string.stack_picker) else stringResource(
            id = R.string.stack_picked
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null,
        title = selectedType?.title ?: "",
        isExpanded = isExpanded
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            StackType.entries.forEach { stackType ->
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = stackType.icon),
                            contentDescription = stringResource(
                                id = R.string.drop_down_item_icon
                            ),
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    text = {
                        Text(
                            text = stackType.title,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            maxLines = 1
                        )
                    },
                    onClick = {
                        onSelect(stackType)
                        isExpanded = false
                    },
                    enabled = selectedType != stackType
                )
            }
        }
    }
}

@Composable
private fun PlatformTypeMenu(
    onSelect: (PlatformType) -> Unit,
    selectedType: PlatformType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) stringResource(id = R.string.platform_picker) else stringResource(
            id = R.string.platform_picked
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null,
        title = selectedType?.title ?: "",
        isExpanded = isExpanded
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            PlatformType.entries.forEach { type ->
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = type.icon),
                            contentDescription = stringResource(
                                id = R.string.drop_down_item_icon
                            ),
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    text = {
                        Text(
                            text = type.title,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            maxLines = 1
                        )
                    },
                    onClick = {
                        onSelect(type)
                        isExpanded = false
                    },
                    enabled = selectedType != type
                )
            }
        }
    }
}