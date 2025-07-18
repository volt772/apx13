package com.apx.linea.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import kotlin.let

class LocalDateConverter {

    @TypeConverter
    fun fromEpochDay(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun toEpochDay(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}
