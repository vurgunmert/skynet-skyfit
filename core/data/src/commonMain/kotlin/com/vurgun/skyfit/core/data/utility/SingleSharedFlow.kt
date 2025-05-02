package com.vurgun.skyfit.core.data.utility

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> SingleSharedFlow(): MutableSharedFlow<T> =
    MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

suspend fun <T> MutableSharedFlow<T>.emitOrNull(value: T) {
    runCatching { emit(value) }.onFailure { it.printStackTrace() }
}
