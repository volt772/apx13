package com.apx.apx108.presentation.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apx.linea.presentation.ui.theme.AxPrimary
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    isSearching: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCloseClick: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    // 🔥 검색 시작 시 자동 포커스
    LaunchedEffect(isSearching) {
        if (isSearching) {
            delay(100) // UI 안정화를 위한 약간의 지연 (필요시)
            focusRequester.requestFocus()
        }
    }

    SmallTopAppBar(
        title = {
            if (isSearching) {
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text("검색어 입력", color = Color.White.copy(alpha = 0.6f))
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .focusRequester(focusRequester),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            } else {
            }
        },
        actions = {
            if (isSearching) {
                IconButton(onClick = onCloseClick) {
                    Icon(Icons.Default.Close, contentDescription = "검색 닫기", tint = Color.White)
                }
            } else {
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Default.Search, contentDescription = "검색", tint = Color.White)
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = AxPrimary
        )
    )
}
