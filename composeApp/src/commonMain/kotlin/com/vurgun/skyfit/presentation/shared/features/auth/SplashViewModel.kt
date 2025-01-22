package com.vurgun.skyfit.presentation.shared.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            // Simulate a delay or check logic for user authentication
            val isLoggedIn = checkLoginStatus()
            if (isLoggedIn) {
                _uiState.value = UIState.Ready
            } else {
                _uiState.value = UIState.Login
            }
        }
    }

    private suspend fun checkLoginStatus(): Boolean {
        // Replace with actual logic to check if the user is logged in
        return false // Example: default to not logged in
    }

    enum class UIState {
        Idle,
        Login,
        Ready
    }
}
