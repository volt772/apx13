package com.apx.linea.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecordConverter {
    @TypeConverter
    fun fromListToJson(value: List<Int>): String = Gson().toJson(value)

    @TypeConverter
    fun fromJsonToList(value: String): List<Int> =
        Gson().fromJson(value, object : TypeToken<List<Int>>() {}.type)
}