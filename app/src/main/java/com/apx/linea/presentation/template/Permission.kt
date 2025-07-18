package com.apx.linea.presentation.template

/**
 * 권한예시
 */
//    /* 권한(지도)*/
//    private val locationPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            checkNotificationPermission()
//        } else {
//            /* 거절 두번 후에*/
//            Toast.makeText(this, "위치 권한이 필요합니다. 설정에서 위치권한을 허용해주세요", Toast.LENGTH_SHORT).show()
//            finish()
//        }
//    }
//
//    /* 권한(위치)*/
//    private val requestNotificationPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                checkExactAlarmPermission()
//            } else {
//                /* 거절 두번 후에*/
//                Toast.makeText(this, "알림 권한이 필요합니다. 설정에서 알림권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }

/* 권한예시*/
//    private fun checkNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                return
//            }
//        }
//        checkExactAlarmPermission() // 권한 있으면 바로 다음으로
//    }

//    override fun onResume() {
//        super.onResume()
//        // 설정에서 돌아온 경우 exact alarm 권한 재확인
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            if (alarmManager.canScheduleExactAlarms()) {
//                launchMainContent()
//            }
//        }
//    }
