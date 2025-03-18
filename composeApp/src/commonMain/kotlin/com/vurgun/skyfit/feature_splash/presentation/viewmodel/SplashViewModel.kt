package com.vurgun.skyfit.feature_splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_splash.domain.model.SplashResult
import com.vurgun.skyfit.feature_splash.domain.usecase.SplashUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SplashUiState {
    data object Loading : SplashUiState()
    data object Maintenance : SplashUiState()
    data object NavigateToLogin : SplashUiState()
    data object NavigateToDashboard : SplashUiState()
}

class SplashViewModel(
    private val splashUseCase: SplashUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            val result = splashUseCase.execute()
            _uiState.value = result.toUiState()
        }
    }

    private fun SplashResult.toUiState(): SplashUiState {
        return when (this) {
            is SplashResult.Maintenance -> SplashUiState.Maintenance
            is SplashResult.UserNotFound -> SplashUiState.NavigateToLogin
            is SplashResult.UserAvailable -> SplashUiState.NavigateToDashboard
        }
    }
}
