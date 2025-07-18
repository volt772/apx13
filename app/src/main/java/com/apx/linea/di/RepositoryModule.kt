package com.apx.linea.di

import com.apx.linea.data.repository.LineaRepositoryImpl
import com.apx.linea.domain.repository.LineaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLineaRepository(lineaRepository: LineaRepositoryImpl): LineaRepository
}