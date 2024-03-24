package com.sedooj.resumen.ui.kit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KitFilledButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Black,
        contentColor = Color.White,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.LightGray
    ),
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    onClick: () -> Unit,
//    content:@Composable () -> Unit
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
            Text(text = label)
        }
    )
}