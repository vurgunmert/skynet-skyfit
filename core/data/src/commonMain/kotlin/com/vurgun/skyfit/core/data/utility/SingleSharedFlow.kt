package com.vurgun.skyfit.core.data.utility

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T> SingleSharedFlow(): MutableSharedFlow<T> =
    MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

suspend fun <T> MutableSharedFlow<T>.emitOrNull(value: T) {
    runCatching { emit(value) }.onFailure { it.printStackTrace() }
}


class UiStateDelegate<T>(initial: T) {
    private val _state = MutableStateFlow(initial)
    val state: StateFlow<T> = _state

    fun update(newState: T) {
        _state.value = newState
    }

    fun asStateFlow(): StateFlow<T> = state
}
