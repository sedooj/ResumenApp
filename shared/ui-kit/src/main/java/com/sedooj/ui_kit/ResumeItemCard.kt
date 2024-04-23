package com.sedooj.ui_kit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

data class ResumeItemState(
    var resumeId: Long,
    var title: String,
)

@Composable
fun ResumeItemCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    resume: ResumeItemState,
    onEditResume: () -> Unit,
    onDropResume: () -> Unit,
    onDownloadResume: () -> Unit,
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = shape,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.surfaceTint,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            disabledContentColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = elevation,
        border = border,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.resume),
                            contentDescription = stringResource(
                                id = R.string.resume
                            ),
                            modifier = Modifier.size(70.dp)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(3f),
                    contentAlignment = Alignment.Center,
                    content = {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(
                                10.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            Text(
                                text = resume.title,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(2f)
                            )
                            ResumeItemButtons(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(3f),
                                onEditResume = { onEditResume() },
                                onDropResume = { onDropResume() },
                                onDownloadResume = { onDownloadResume() }
                            )
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun ResumeItemButtons(
    modifier: Modifier = Modifier,
    onEditResume: () -> Unit,
    onDropResume: () -> Unit,
    onDownloadResume: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = { onEditResume() },
            icon = painterResource(id = R.drawable.edit_resume),
            contentDescription = stringResource(id = R.string.edit_resume),
        )
        FilledIconButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = { onDropResume() },
            icon = painterResource(id = R.drawable.trash),
            contentDescription = stringResource(id = R.string.drop_resume),
        )
        FilledIconButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = { onDownloadResume() },
            icon = painterResource(id = R.drawable.download),
            contentDescription = stringResource(id = R.string.download_resume),
            enabled = false
        )
    }
}