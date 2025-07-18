package com.apx.linea.presentation.dialog

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.apx.linea.R
import com.apx.linea.presentation.ui.theme.AxGray400
import com.apx.linea.presentation.ui.theme.AxGray500
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


@Composable
fun LineaDateField(
    purchaseDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val themedContext = ContextThemeWrapper(context, R.style.CustomDatePickerDialogTheme)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // 날짜 필드 (읽기 전용)
        OutlinedTextField(
            value = purchaseDate.format(formatter),
            onValueChange = {},
            label = { Text("일자") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(0f), // 아래에 깔림
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "날짜 선택", tint = AxGray500)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AxGray400,
                unfocusedBorderColor = AxGray400
            )
        )

        // 투명 클릭 박스
        Box(
            modifier = Modifier
                .matchParentSize()
                .zIndex(1f) // 위에 올라감
                .background(Color.Transparent)
                .clickable {
                    val calendar = Calendar.getInstance().apply {
                        set(purchaseDate.year, purchaseDate.monthValue - 1, purchaseDate.dayOfMonth)
                    }
                    DatePickerDialog(
                        themedContext,
                        { _, year, month, dayOfMonth ->
                            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
        )
    }
}
