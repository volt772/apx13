package com.apx.linea.domain.repository

import com.apx.linea.domain.model.LineaModel
import kotlinx.coroutines.flow.Flow

interface LineaRepository {
    suspend fun insertLinea(linea: LineaModel)

    suspend fun updateLinea(linea: LineaModel)

    suspend fun deleteLinea(linea: LineaModel)

    fun getAllLineas(): Flow<List<LineaModel>>

    fun searchLineaByName(query: String): Flow<List<LineaModel>>

    suspend fun getLineaById(id: Long): LineaModel?
}