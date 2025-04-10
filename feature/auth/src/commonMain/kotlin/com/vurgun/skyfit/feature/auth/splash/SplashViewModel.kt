package com.vurgun.skyfit.feature.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.auth.domain.model.SplashResult
import com.vurgun.skyfit.data.auth.domain.usecase.SplashUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SplashUiState.Loading
        )

    private fun loadData() {
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
