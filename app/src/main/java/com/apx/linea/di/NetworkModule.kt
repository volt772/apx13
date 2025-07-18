package com.apx.linea.di

import com.apx.linea.data.remote.LineaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl = "https://api/"

    @Provides
    @Singleton
    fun provideApxApi(
        @ApxHttpClient okHttpClient: OkHttpClient,
    ): LineaApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @ApxHttpClient
    fun provideApxHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addInterceptor(loggingInterceptor)
        }.build()
    }
}
