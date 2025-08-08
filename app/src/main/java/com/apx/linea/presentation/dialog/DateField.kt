package com.apx.linea.presentation.dialog

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.apx.linea.presentation.widget.AppOutlinedTextField
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.let


@Composable
fun DateField(
    applyDate: Long,
    title: String,
    onDateSelected: (LocalDate) -> Unit
) {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val initialDate: LocalDate? = remember(applyDate) {
        if (applyDate > 0L) {
            Instant.ofEpochMilli(applyDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        } else null
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(initialDate) }

    if (showDatePicker) {
        DatePickerModal(
            initialDateMillis = if (applyDate > 0L) {
                LocalDate.ofEpochDay(
                    Instant.ofEpochMilli(applyDate)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .toEpochDay()
                ).atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli()
            } else null,
            onDateSelected = { millis ->
                val localDate = millis?.let {
                    // ✅ 날짜를 선택한 경우
                    Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                } ?: run {
                    // ❌ 날짜 선택 안 한 경우
                    val fallbackMillis = if (applyDate > 0L) applyDate else
                        LocalDate.now()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()

                    Instant.ofEpochMilli(fallbackMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
                selectedDate = localDate
                localDate?.let { onDateSelected(it) }
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AppOutlinedTextField(
            value = selectedDate?.format(formatter) ?: "",
            onValueChange = { },
            label = { Text(title) },
            placeholder = { Text("년/월/일") },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "날짜선택")
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                        // in the Initial pass to observe events before the text field consumes them
                        // in the Main pass.
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showDatePicker = true
                        }
                    }
                }
        )
    }
}
