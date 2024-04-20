package com.sedooj.app_ui.pages.resume.create.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sedooj.arch.viewmodel.auth.resume.CreateResumeViewModel
import com.sedooj.ui_kit.R

@Composable
fun ResumeOptionsComponent(
    createResumeViewModel: CreateResumeViewModel,
) {
    val uiState = createResumeViewModel.uiState.collectAsState().value
    TitleTextField(onValueChange = {
        createResumeViewModel.updateTitle(input = it)
    }, value = uiState.title ?: "")
}

@Composable
private fun TitleTextField(
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
            Text(text = stringResource(id = R.string.resume_name))
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        isError = false,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}