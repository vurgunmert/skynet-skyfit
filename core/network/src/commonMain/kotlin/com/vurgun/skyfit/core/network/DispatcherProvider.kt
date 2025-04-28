package com.vurgun.skyfit.core.network

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatcherProvider() {
    val io: CoroutineDispatcher
}