package com.vurgun.skyfit.feature.access.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.auth.model.SplashResult
import com.vurgun.skyfit.core.data.v1.domain.auth.usercase.SplashUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal sealed interface SplashUiEvent {
    data object NavigateToMaintenance : SplashUiEvent
    data object NavigateToAuth : SplashUiEvent
    data object NavigateMain : SplashUiEvent
}

internal class SplashViewModel(
    private val splashUseCase: SplashUseCase
) : ScreenModel {

    private val _eventChannel = Channel<SplashUiEvent>(Channel.UNLIMITED)
    val eventFlow: Flow<SplashUiEvent> = _eventChannel.receiveAsFlow()

    init {
        screenModelScope.launch {
            delay(2000L)

            val event = resolveSplashEvent()
            _eventChannel.send(event)
        }
    }


    private suspend fun resolveSplashEvent(): SplashUiEvent {
        return runCatching { splashUseCase.execute() }
            .map { result ->
                when (result) {
                    SplashResult.Maintenance   -> SplashUiEvent.NavigateToMaintenance
                    SplashResult.UserNotFound  -> SplashUiEvent.NavigateToAuth
                    SplashResult.UserAvailable -> SplashUiEvent.NavigateMain
                }
            }
            .getOrElse { error ->
                logError(error)
                SplashUiEvent.NavigateToAuth
            }
    }

    private fun logError(error: Throwable) {
        // Swap this with your preferred logger
        println("SplashViewModel Error: ${error.message}")
    }
}
