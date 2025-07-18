package com.apx.linea.di

import javax.inject.Qualifier

/* HttpClients*/
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApxHttpClient


/**
 * @Related
 * Dispatchers
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher