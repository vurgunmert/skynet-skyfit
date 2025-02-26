package com.vurgun.skyfit.core.utils

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatcherProvider() {
    val io: CoroutineDispatcher
}