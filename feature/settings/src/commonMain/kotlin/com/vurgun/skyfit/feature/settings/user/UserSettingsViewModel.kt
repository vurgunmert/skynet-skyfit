package com.vurgun.skyfit.feature.settings.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.storage.LocalSettingsStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface UserSettingsViewEvent {
    data object GoToLogin : UserSettingsViewEvent
}

class UserSettingsViewModel(private val store: LocalSettingsStore) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UserSettingsViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun onLogout() {
        viewModelScope.launch {
            store.clearAll()
            _uiEvents.emit(UserSettingsViewEvent.GoToLogin)
        }
    }
}