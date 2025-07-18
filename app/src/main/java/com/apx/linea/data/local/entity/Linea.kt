package com.apx.linea.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "linea")
data class Linea(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val number: String,
    val memo: String?,
    val mydate: LocalDate,
    val photoPath: String?,

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: String? = null
)
