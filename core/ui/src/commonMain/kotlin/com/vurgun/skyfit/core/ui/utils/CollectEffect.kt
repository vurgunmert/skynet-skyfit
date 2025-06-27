package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun <T> CollectEffect(
    effect: SharedFlow<T>,
    debounceMillis: Long = 250L,
    onEffect: suspend (T) -> Unit
) {
    LaunchedEffect(effect) {
        effect
            .debounce(debounceMillis)
            .distinctUntilChanged()
            .collectLatest { action ->
                onEffect(action)
            }
    }
}