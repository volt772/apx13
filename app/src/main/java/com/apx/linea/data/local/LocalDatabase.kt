package com.apx.linea.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apx.linea.data.local.converter.LocalDateConverter
import com.apx.linea.data.local.dao.LineaDao
import com.apx.linea.data.local.entity.Linea

@Database(
    entities = [
        Linea::class
   ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun lineaDao(): LineaDao
}