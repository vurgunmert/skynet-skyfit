package com.vurgun.skyfit.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class DispatcherProvider actual constructor() {
    actual val io: CoroutineDispatcher
        get() =  Dispatchers.Unconfined
}