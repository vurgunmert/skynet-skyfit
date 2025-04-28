package com.vurgun.skyfit.core.network.utils

import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

suspend fun <T : Any, R> ApiResult<T>.mapOrThrow(transform: (T) -> R): R {
    return when (this) {
        is ApiResult.Success -> transform(data)
        is ApiResult.Error -> {
            this.code
            throw IllegalStateException(message)
        }
        is ApiResult.Exception -> throw exception
    }
}

suspend inline fun <T> ioResult(
    dispatchers: DispatcherProvider,
    crossinline block: suspend CoroutineScope.() -> T
): Result<T> = withContext(dispatchers.io) {
    runCatching { block() }
}
