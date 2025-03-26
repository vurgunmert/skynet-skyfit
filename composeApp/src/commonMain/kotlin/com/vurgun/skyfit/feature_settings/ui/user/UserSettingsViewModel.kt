package com.vurgun.skyfit.feature_settings.ui.user

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.core.storage.LocalSettingsStore

sealed interface UserSettingsViewEvent {
    data object GoToLogin: UserSettingsViewEvent
}

class UserSettingsViewModel(private val store: LocalSettingsStore): ViewModel() {

    fun onLogout() {
        store.clearAll()
    }
}