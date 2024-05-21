package com.sedooj.ui_kit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sedooj.ui_kit.R

@Composable
fun FloatingSaveButton(
    onSave: () -> Unit,
    isDataSaved: Boolean,
    isDataEdited: Boolean
) {
    AnimatedVisibility(
        visible = !isDataSaved && isDataEdited, enter = scaleIn(tween(200)), exit = scaleOut(
            tween(200)
        )
    ) {
        FloatingActionButton(onClick = {
            onSave()
        }) {
            Icon(
                imageVector = Icons.Outlined.Done,
                contentDescription = stringResource(id = R.string.done)
            )
        }
    }
}