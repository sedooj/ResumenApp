package com.sedooj.ui_kit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FilledIconButton(
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(6.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Black,
        contentColor = Color.White,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.LightGray
    ),
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String = "Icon",
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
            Row {
                Icon(
                    painter = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(50.dp)
                )
                if (label != null) {
                    Text(text = label, textAlign = TextAlign.Center)
                }
            }
        }
    )
}