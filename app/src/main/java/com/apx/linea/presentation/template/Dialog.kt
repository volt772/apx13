package com.apx.linea.presentation.template

/* 다이얼로그 상태*/
//            val openAlertDialog = remember { mutableStateOf(isFirstLaunch) }

/**
 * OpenAlertDialog
 * @desc 첫 실행시 안내문구 (앱 사용중 최초1회만 노출)
 * @action `확인` Preference에 첫실행여부 플래그 Boolean값 설정
 */
//            if (openAlertDialog.value) {
//                AppInfoDialog(
//                    onDismissRequest = { },
//                    onConfirmation = {
//                        openAlertDialog.value = false
//                        vm.setIsFirstRun()
//                    },
//                    dialogTitle = stringResource(id = R.string.welcome),
//                    dialogText = stringResource(id = R.string.inaccurate_info1),
//                    icon = Icons.Default.Face,
//                    buttonConfirmLabel = stringResource(id = R.string.confirmed)
//                )
//            }
