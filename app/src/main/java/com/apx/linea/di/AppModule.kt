package com.apx.linea.di

import android.app.Application
import android.content.Context
import com.apx.linea.preference.PrefManager
import com.apx.linea.preference.PrefManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindPreferences(impl: PrefManagerImpl): PrefManager

}