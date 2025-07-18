package com.apx.linea.data.repository

import com.apx.linea.data.local.dao.LineaDao
import com.apx.linea.data.mappers.toEntity
import com.apx.linea.data.mappers.toModel
import com.apx.linea.domain.model.LineaModel
import com.apx.linea.domain.repository.LineaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LineaRepositoryImpl @Inject constructor(
    private val dao: LineaDao
) : LineaRepository {
    override suspend fun insertLinea(linea: LineaModel) {
        dao.insert(linea.toEntity())
    }

    override suspend fun updateLinea(linea: LineaModel) {
        dao.update(linea.toEntity())
    }

    override suspend fun deleteLinea(linea: LineaModel) {
        dao.delete(linea.toEntity())
    }

    override fun getAllLineas(): Flow<List<LineaModel>> {
        return dao.getAll().map { list -> list.map { it.toModel() } }
    }

    override fun searchLineaByName(query: String): Flow<List<LineaModel>> {
        return dao.searchLineaByName(query).map { list -> list.map { it.toModel() } }
    }

    override suspend fun getLineaById(id: Long): LineaModel? {
        return dao.getById(id)?.toModel()
    }}
