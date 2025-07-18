package com.apx.linea.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class LineaModel(
    val id: Long = 0L,
    val name: String,
    val number: String,
    val memo: String?,
    val mydate: LocalDate,
    val photoPath: String?,
    val createdAt: String?
) : Parcelable
