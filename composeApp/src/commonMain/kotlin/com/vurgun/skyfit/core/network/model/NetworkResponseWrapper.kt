package com.vurgun.skyfit.core.network.model

sealed class NetworkResponseWrapper<out T> {
    data class Success<T>(
        val status: String = "success",
        val title: String = "Request Successful",
        val message: String = "The operation was completed successfully.",
        val data: T,
        val errorInputs: List<String> = emptyList(),
        val logout: Int = 0
    ) : NetworkResponseWrapper<T>()

    data class Error(
        val status: String = "error",
        val title: String = "Request Failed",
        val message: String,
        val data: Any? = null,
        val errorInputs: List<String> = emptyList(),
        val logout: Int = 0
    ) : NetworkResponseWrapper<Nothing>()
}

