package com.vurgun.skyfit.core.data.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun <T> MutableSharedFlow<T>.emitIn(viewModelScope: CoroutineScope, value: T) {
    viewModelScope.launch {
        emit(value)
    }
}