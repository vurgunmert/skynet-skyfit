package com.vurgun.skyfit.data.network

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatcherProvider() {
    val io: CoroutineDispatcher
}