package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun <T> CollectEffect(
    effect: SharedFlow<T>,
    onEffect: suspend (T) -> Unit
) {
    LaunchedEffect(effect) {
        effect.collect { onEffect(it) }
    }
}