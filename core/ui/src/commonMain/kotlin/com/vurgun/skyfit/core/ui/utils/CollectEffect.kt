package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * @deprecated Use CollectEvent instead for better handling of one-time events
 */
@Deprecated("Use CollectEvent instead for better handling of one-time events", ReplaceWith("CollectEvent(effect, onEffect)"))
@OptIn(FlowPreview::class)
@Composable
fun <T> CollectEffect(
    effect: SharedFlow<T>,
    debounceMillis: Long = 250L,
    onEffect: suspend (T) -> Unit
) {
    LaunchedEffect(effect) {
        effect
            .distinctUntilChanged()
            .debounce(debounceMillis)
            .collect { action ->
                onEffect(action)
            }
    }
}

/**
 * Collects events from a Flow in a Composable.
 * This is the preferred way to handle one-time events in Compose UI.
 * 
 * @param eventFlow The flow of events to collect
 * @param onEvent The callback to handle each event
 */
@Composable
fun <T> CollectEvent(
    eventFlow: Flow<T>,
    onEvent: (T) -> Unit
) {
    LaunchedEffect(eventFlow) {
        eventFlow.collect { event ->
            onEvent(event)
        }
    }
}
