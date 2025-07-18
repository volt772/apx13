package com.apx.linea.presentation.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.apx.linea.presentation.ui.theme.AxRed600
import com.apx.linea.presentation.ui.theme.AxStatusBarDarkColor
import com.apx.linea.presentation.ui.theme.AxStatusBarLightColor
import com.apx.linea.presentation.ui.theme.AxWhite
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * 상단 상태바 색상지정
 * @desc Light, Dark 모드 색상구분
 * @desc 색상은 Color에서 수정함
 */
@Composable
fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        /* 하단 `네비게이션바` 컬러*/
        systemUiController.setNavigationBarColor(
            color = if (useDarkIcons) {
                AxStatusBarLightColor
            } else {
                AxStatusBarDarkColor
            },
            darkIcons = useDarkIcons
        )
        /**
         * 상단 `상태바` 컬러
         * @desc 다크모드일 경우에는 흰색으로 변경 (원색으로 지정하면 폰트가 안보임)
         */
        systemUiController.setStatusBarColor(
            color = if (useDarkIcons) {
                AxStatusBarDarkColor
            } else {
                AxStatusBarLightColor
            },
            darkIcons = useDarkIcons
        )

        onDispose { }
    }
}

/**
 * Top AppBar With `Back`
 * `뒤로가기`버튼이 있는 AppBar
 * @param title 타이틀
 * @param content 내용
 * @param backPressed 뒤로가기 눌렀을때의 함수
 */
@ExperimentalMaterial3Api
@Composable
fun BackButtonScaffoldScreen(
    title: String,
    content: @Composable () -> Unit,
    backPressed: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults
        .pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        /**
         * @desc 타이틀 : 타이틀
         * @desc 아이콘 : ArrowBack으로 지정한다.
         */
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AxRed600,
                    scrolledContainerColor = AxRed600,
                ),
                title = {
                    Text(
                        text = title,
                        color = AxWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backPressed.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기", tint = AxWhite
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AxWhite)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            content()
        }
    }
}
