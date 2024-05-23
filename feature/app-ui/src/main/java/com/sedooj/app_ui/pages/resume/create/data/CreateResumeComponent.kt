package com.sedooj.app_ui.pages.resume.create.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sedooj.ui_kit.R.string
import com.sedooj.ui_kit.fields.FilledButton

@Deprecated("No longer needed")
@Composable
fun DoneBox(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Done, contentDescription = stringResource(
                    id = string.resume_creation_done
                ),
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Color(26, 170, 15, 255), shape = CircleShape)
                    .padding(5.dp),
                tint = Color.White
            )
            Text(
                text = stringResource(id = string.resume_creation_done),
                textAlign = TextAlign.Center
            )
            FilledButton(
                label = stringResource(id = string.next),
                onClick = onClick
            )
        }
    }
}

@Composable
fun ErrorBox(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = com.sedooj.ui_kit.R.drawable.warning),
                contentDescription = stringResource(
                    id = string.error_create_resume
                ),
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = stringResource(id = string.error_create_resume),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            FilledButton(
                label = stringResource(id = string.back_to_main_menu),
                onClick = onClick,
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            )
        }
    }
}