package com.sedooj.ui_kit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CheckButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(10.dp),
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    onClick: () -> Unit,
    isChecked: Boolean,
) {
    val notCheckedColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = MaterialTheme.colorScheme.surfaceTint,
    )
    val checkedColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surfaceTint,
        contentColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onClick()
            },
            modifier = modifier.height(40.dp),
            enabled = enabled,
            shape = shape,
            colors = if (!isChecked) notCheckedColors else checkedColors,
            contentPadding = contentPadding,
            content = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = label,
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }
        )
        CheckMark(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart),
            isChecked = isChecked,
            colors = if (isChecked) notCheckedColors else checkedColors
        )
    }
}

@Composable
fun CheckMark(
    modifier: Modifier,
    isChecked: Boolean,
    colors: ButtonColors,
) {
    Icon(
        painter = painterResource(id = R.drawable.empty_check_box),
        contentDescription = stringResource(
            id = R.string.empty_check_box
        ),
        modifier = modifier
            .size(40.dp)
            .padding(start = 15.dp),
        tint = colors.containerColor
    )
    AnimatedVisibility(
        modifier = modifier.padding(start = 20.dp),
        visible = isChecked,
        enter = scaleIn(tween(200)),
        exit = scaleOut(tween(200))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.check_mark),
            contentDescription = stringResource(
                id = R.string.check_mark
            ),
            modifier = Modifier.size(15.dp),
            tint = colors.containerColor
        )
    }
}