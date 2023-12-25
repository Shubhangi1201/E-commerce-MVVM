package com.example.e_commerceapp.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String ?= null
){
    class success<T>(data:T): Resource<T>(data)
    class Error<T>(message: String): Resource<T>(message = message)
    class loading<T> : Resource<T>()
}
