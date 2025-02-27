package com.vurgun.skyfit.feature_onboarding.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseOnboardingViewModel : ViewModel() {
    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    fun completeOnboarding() {
        _isCompleted.value = true
    }
}
