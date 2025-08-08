package com.apx.linea.presentation.widget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        modifier = modifier,
        maxLines = maxLines,
        singleLine = singleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF6A7EFC),   // ✅ 원하는 스타일
            unfocusedBorderColor = Color(0xFFCBD5E1), // ✅ 원하는 스타일
            disabledBorderColor = Color.LightGray,
            errorBorderColor = Color.Red
        )
    )
}
