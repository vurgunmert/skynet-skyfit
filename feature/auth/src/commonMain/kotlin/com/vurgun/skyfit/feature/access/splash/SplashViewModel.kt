package com.vurgun.skyfit.feature.access.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.domain.auth.model.SplashResult
import com.vurgun.skyfit.core.data.v1.domain.auth.usercase.SplashUseCase
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class SplashUiState {
    data object Loading : SplashUiState()
    data class Error(val message: String?) : SplashUiState()
}

internal sealed interface SplashEffect {
    data object NavigateToMaintenance : SplashEffect
    data object NavigateToAuth : SplashEffect
    data object NavigateToDashboard : SplashEffect
}

internal class SplashViewModel(
    private val splashUseCase: SplashUseCase
) : ScreenModel {

    private val _uiState = UiStateDelegate<SplashUiState>(SplashUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<SplashEffect>()
    val effect: SharedFlow<SplashEffect> = _effect

    fun loadData() {
        _uiState.update(SplashUiState.Loading)

        screenModelScope.launch {
            try {
                val result = splashUseCase.execute()
                _effect.emitOrNull(result.toEffect())
            } catch (e: Exception) {
                _uiState.update(SplashUiState.Error(e.message))
            }
        }
    }

    private fun SplashResult.toEffect(): SplashEffect {
        return when (this) {
            is SplashResult.Maintenance -> SplashEffect.NavigateToMaintenance
            is SplashResult.UserNotFound -> SplashEffect.NavigateToAuth
            is SplashResult.UserAvailable -> SplashEffect.NavigateToDashboard
        }
    }
}
