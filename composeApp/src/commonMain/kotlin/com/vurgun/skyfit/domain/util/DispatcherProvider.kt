package com.vurgun.skyfit.domain.util

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatcherProvider() {
    val io: CoroutineDispatcher
}
