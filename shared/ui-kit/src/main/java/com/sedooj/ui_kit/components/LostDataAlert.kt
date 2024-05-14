package com.sedooj.ui_kit.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R
import com.sedooj.ui_kit.fields.FilledButton

@Composable
fun LostDataAlert(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.warning),
                contentDescription = stringResource(
                    id = R.string.warning
                ),
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.changes_is_not_saved),
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        dismissButton = {
            FilledButton(
                label = stringResource(id = R.string.quit),
                onClick = { onConfirm() },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            )
        },
        confirmButton = {
            FilledButton(
                label = stringResource(id = R.string.continue_edit),
                onClick = { onDismiss() },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            )
        },
        modifier = modifier
    )
}