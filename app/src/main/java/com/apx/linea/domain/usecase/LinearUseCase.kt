package com.apx.linea.domain.usecase

import com.apx.linea.domain.model.LineaModel
import com.apx.linea.domain.repository.LineaRepository
import kotlinx.coroutines.flow.Flow

data class LineaUseCase(
    val insertLinea: InsertLinea,
    val updateLinea: UpdateLinea,
    val deleteLinea: DeleteLinea,
    val getAllLineas: GetAllLineas,
    val getLineaById: GetLineaById,
    val searchLineaByName: SearchLineaByName
)

class InsertLinea(private val repository: LineaRepository) {
    suspend operator fun invoke(linea: LineaModel) {
        repository.insertLinea(linea)
    }
}

class UpdateLinea(private val repository: LineaRepository) {
    suspend operator fun invoke(linea: LineaModel) {
        repository.updateLinea(linea)
    }
}

class DeleteLinea(private val repository: LineaRepository) {
    suspend operator fun invoke(linea: LineaModel) {
        repository.deleteLinea(linea)
    }
}

class GetAllLineas(private val repository: LineaRepository) {
    operator fun invoke(): Flow<List<LineaModel>> {
        return repository.getAllLineas()
    }
}

class GetLineaById(private val repository: LineaRepository) {
    suspend operator fun invoke(id: Long): LineaModel? {
        return repository.getLineaById(id)
    }
}

class SearchLineaByName(private val repository: LineaRepository) {
    operator fun invoke(query: String): Flow<List<LineaModel>> {
        return repository.searchLineaByName(query)
    }
}