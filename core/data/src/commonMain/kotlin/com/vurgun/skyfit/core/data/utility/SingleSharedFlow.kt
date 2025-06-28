package com.vurgun.skyfit.core.data.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

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

/**
 * @deprecated Use UiEventDelegate instead for better handling of one-time events
 */
@Deprecated("Use UiEventDelegate instead for better handling of one-time events", ReplaceWith("UiEventDelegate<T>()"))
class UiEffectDelegate<T> {
    private val _effect = SingleSharedFlow<T>()
    val effect: SharedFlow<T> = _effect

    suspend fun emit(effect: T) {
        _effect.emitOrNull(effect)
    }

    fun emitIn(viewModelScope: CoroutineScope, effect: T) {
        _effect.emitIn(viewModelScope, effect)
    }

    fun asSharedFlow(): SharedFlow<T> = effect
}

/**
 * A delegate for handling one-time UI events using Kotlin Channels.
 * This approach is preferred over SharedFlow for events that should only be processed once.
 */
class UiEventDelegate<T> {
    private val _eventChannel = Channel<T>()
    val eventFlow = _eventChannel.receiveAsFlow()

    suspend fun send(event: T) {
        _eventChannel.send(event)
    }

    fun sendIn(scope: CoroutineScope, event: T) {
        scope.launch {
            _eventChannel.send(event)
        }
    }
}
