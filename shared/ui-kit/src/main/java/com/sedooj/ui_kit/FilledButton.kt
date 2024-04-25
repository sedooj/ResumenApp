package com.sedooj.ui_kit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surfaceTint,
        contentColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.LightGray
    ),
    onClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    icon: Painter? = null
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Box(modifier = Modifier.fillMaxWidth().padding(start = 10.dp).weight(1f), contentAlignment = Alignment.CenterStart) {
                        Icon(
                            painter = icon,
                            contentDescription = label,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxWidth().weight(4f), contentAlignment = if (icon != null) Alignment.CenterStart else Alignment.Center) {
                    Text(text = label, textAlign = TextAlign.Center)
                }
            }
        }
    )
}