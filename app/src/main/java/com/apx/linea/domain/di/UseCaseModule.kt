package com.apx.linea.domain.di

import com.apx.linea.domain.repository.LineaRepository
import com.apx.linea.domain.usecase.DeleteLinea
import com.apx.linea.domain.usecase.GetAllLineas
import com.apx.linea.domain.usecase.GetLineaById
import com.apx.linea.domain.usecase.InsertLinea
import com.apx.linea.domain.usecase.LineaUseCase
import com.apx.linea.domain.usecase.SearchLineaByName
import com.apx.linea.domain.usecase.UpdateLinea
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideLineaUseCases(
        repository: LineaRepository
    ): LineaUseCase {
        return LineaUseCase(
            insertLinea = InsertLinea(repository),
            updateLinea = UpdateLinea(repository),
            deleteLinea = DeleteLinea(repository),
            getAllLineas = GetAllLineas(repository),
            getLineaById = GetLineaById(repository),
            searchLineaByName = SearchLineaByName(repository)
        )
    }
}