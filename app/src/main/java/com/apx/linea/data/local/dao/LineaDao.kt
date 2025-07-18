package com.apx.linea.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.apx.linea.data.local.entity.Linea
import kotlinx.coroutines.flow.Flow

@Dao
interface LineaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(linea: Linea)

    @Update
    suspend fun update(linea: Linea)

    @Delete
    suspend fun delete(linea: Linea)

    @Query("SELECT * FROM linea ORDER BY created_at DESC, id DESC")
    fun getAll(): Flow<List<Linea>>

    @Query("""
        SELECT *
        FROM linea
        WHERE name LIKE '%' || :query || '%' or number LIKE '%' || :query || '%' or memo LIKE '%' || :query || '%'
        ORDER BY created_at DESC, id DESC
    """)
    fun searchLineaByName(query: String): Flow<List<Linea>>

    @Query("SELECT * FROM linea WHERE id = :id")
    suspend fun getById(id: Long): Linea?
}
