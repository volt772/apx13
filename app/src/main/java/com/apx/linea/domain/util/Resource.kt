package com.apx.linea.domain.util

sealed class Resource<T> {
    class Success<T>(val data: T): Resource<T>()
    class Failed<T>(val message: String): Resource<T>()
}