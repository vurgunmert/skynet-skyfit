package com.vurgun.skyfit.feature.access.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.auth.model.SplashResult
import com.vurgun.skyfit.core.data.v1.domain.auth.usercase.SplashUseCase
import kotlinx.coroutines.channels.Channel
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

    private val _eventChannel = Channel<SplashUiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        screenModelScope.launch {
            try {
                val result = splashUseCase.execute()
                val event = when (result) {
                    is SplashResult.Maintenance -> SplashUiEvent.NavigateToMaintenance
                    is SplashResult.UserNotFound -> SplashUiEvent.NavigateToAuth
                    is SplashResult.UserAvailable -> SplashUiEvent.NavigateMain
                }
                _eventChannel.send(event)
            } catch (e: Exception) {
                _eventChannel.send(SplashUiEvent.NavigateToAuth)
            }
        }
    }
}
