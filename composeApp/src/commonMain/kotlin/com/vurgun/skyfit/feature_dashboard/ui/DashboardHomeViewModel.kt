package com.vurgun.skyfit.feature_dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.domain.models.UserDetail
import com.vurgun.skyfit.core.domain.repository.UserRepository
import com.vurgun.skyfit.feature_splash.domain.model.SplashResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardHomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserDetail?>(null)
    val user: StateFlow<UserDetail?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getUserDetails().fold(
                onSuccess = {
                    _user.value = it
                },
                onFailure = { SplashResult.UserNotFound }
            )
            _isLoading.value = false
        }
    }
}