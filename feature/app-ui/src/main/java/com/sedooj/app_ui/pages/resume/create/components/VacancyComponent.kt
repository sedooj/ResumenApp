package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sedooj.api.domain.data.types.BusynessType
import com.sedooj.api.domain.data.types.PlatformType
import com.sedooj.api.domain.data.types.StackType
import com.sedooj.ui_kit.CheckButton
import com.sedooj.ui_kit.NotNullableValueTextField
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.SalaryTextField


/*
        var scheduleType: ScheduleType,
        var readyForTravelling: Boolean,
* */
@Composable
fun VacancyComponent(
    stackType: StackType?,
    platformType: PlatformType?,
    desiredRole: String,
    desiredSalary: String,
    busynessType: BusynessType?,
    onStackSelect: (StackType) -> Unit,
    onPlatformSelect: (PlatformType) -> Unit,
    onRoleValueChange: (String) -> Unit,
    onSalaryValueChange: (String) -> Unit,
    onBusynessSelect: (BusynessType) -> Unit,
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
    NotNullableValueTextField(
        label = R.string.desired_role,
        onValueChange = {
            onRoleValueChange(it)
        },
        value = desiredRole
    )
    BusynessTypeMenu(
        onSelect = { onBusynessSelect(it) },
        selectedType = busynessType
    )
    SalaryTextField(
        label = R.string.desired_salary,
        onValueChange = {
            onSalaryValueChange(it)
        },
        value = desiredSalary
    )
}

@Composable
private fun BusynessTypeMenu(
    onSelect: (BusynessType) -> Unit,
    selectedType: BusynessType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    CheckButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) "${
            stringResource(
                id = R.string.busyness_picked
            )
        }: ${stringResource(id = selectedType.title)}" else stringResource(
            id = R.string.busyness_picker
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null
    )
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

@Composable
private fun StackTypeMenu(
    onSelect: (StackType) -> Unit,
    selectedType: StackType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    CheckButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) "${
            stringResource(
                id = R.string.stack_picked
            )
        }: ${selectedType.title}" else stringResource(
            id = R.string.stack_picker
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null
    )
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

@Composable
private fun PlatformTypeMenu(
    onSelect: (PlatformType) -> Unit,
    selectedType: PlatformType?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    CheckButton(
        modifier = Modifier.fillMaxWidth(),
        label = if (selectedType != null) "${
            stringResource(
                id = R.string.platform_picked
            )
        }: ${selectedType.title}" else stringResource(
            id = R.string.platform_picker
        ),
        onClick = {
            isExpanded = true
        },
        isChecked = selectedType != null
    )
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

@Composable
private fun RoleTextField(
    onValueChange: (String) -> Unit,
    value: String,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = stringResource(id = R.string.desired_role))
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        isError = value.isBlank(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}