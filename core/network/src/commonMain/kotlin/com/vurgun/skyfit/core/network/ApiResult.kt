package com.vurgun.skyfit.core.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val status: String,
        val title: String?,
        val message: String?,
        val code: Int? = null
    ) : ApiResult<Nothing>()

    data class Exception(val exception: Throwable) : ApiResult<Nothing>()
}
